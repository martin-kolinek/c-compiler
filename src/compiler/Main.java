package compiler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import modifiers.ArraySizeInitializerModifier;
import modifiers.AssignmentModifier;
import modifiers.CommaExpressionModifier;
import modifiers.FunctionParameterModifier;
import modifiers.InitializerFillModifier;
import modifiers.InitializerModifier;
import modifiers.LoopModifier;
import modifiers.PointerModifier;
import modifiers.RelationalExpressionModifier;
import modifiers.ReturnModifierFactory;
import modifiers.UnaryChargeModifier;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;

import position.GlobalErrorCounter;
import position.GlobalPositionTracker;

import codegen.MainCodeGenVisitor;

import declaration.DeclarationResolver;
import exceptions.SemanticException;

import toplevel.FunctionDefinition;
import toplevel.InBlock;
import toplevel.Program;
import transformers.BlockModifier;
import transformers.BlockModifierFactory;
import transformers.EmptyBlockModifier;
import transformers.ExpressionModifier;
import transformers.ExpressionModifierFactory;
import transformers.StatementModifier;
import transformers.StatementModifierFactory;
import transformers.TransformerUtil;
import typeresolve.TypeResolverFactory;
import types.EnumRemoverFactory;
import types.TypeCompletenessFactory;
import types.TypedefRemoverFactory;

import grammar.generated.cgrammarLexer;
import grammar.generated.cgrammarParser;

public class Main {
	public static void main(String[] args) throws Exception {
		if(args.length!=2) {
			System.out.println("Expecting 2 file names");
			System.exit(1);
		}
		//read input using antlr
		ANTLRFileStream in = new ANTLRFileStream(args[0]);
		cgrammarLexer lex = new cgrammarLexer(in);
		CommonTokenStream tokens = new CommonTokenStream(lex);
		cgrammarParser pars = new cgrammarParser(tokens);
		Program prog = pars.program().ret;
		GlobalPositionTracker.pos=pars.pos;
		GlobalErrorCounter.errors = 0;
		if(pars.failed())
			GlobalErrorCounter.errors++;

		OutputStreamWriter wr = null;
		try {

			//remove other loops than while
			TransformerUtil.transformProgram(prog, new StatementModifierFactory() {
				@Override
				public StatementModifier create() {
					return new LoopModifier();
				}
			});

			//resolve declarations
			TransformerUtil.transformProgram(prog, new BlockModifierFactory() {
				@Override
				public void popModifierStack() {
				}
				@Override
				public BlockModifier createModifier(FunctionDefinition def) {
					return new DeclarationResolver();
				}
			});

			//remove single expression comma expressions
			TransformerUtil.transformProgram(prog, new ExpressionModifierFactory() {

				@Override
				public ExpressionModifier create() {
					return new CommaExpressionModifier();
				}
			});

			//remove [] and -> expressions
			TransformerUtil.transformProgram(prog, new ExpressionModifierFactory() {

				@Override
				public ExpressionModifier create() {
					return new PointerModifier();
				}
			});

			//remove compound assignments
			TransformerUtil.transformProgram(prog, new ExpressionModifierFactory() {

				@Override
				public ExpressionModifier create() {
					return new AssignmentModifier();
				}
			});

			//remove all relational operators but == and >
			TransformerUtil.transformProgram(prog, new ExpressionModifierFactory() {

				@Override
				public ExpressionModifier create() {
					return new RelationalExpressionModifier();
				}
			});

			//remove unary +,- operators
			TransformerUtil.transformProgram(prog, new ExpressionModifierFactory() {

				@Override
				public ExpressionModifier create() {
					return new UnaryChargeModifier();
				}
			});

			//remove typedefs
			TransformerUtil.transformProgram(prog, new TypedefRemoverFactory());

			//remove enums
			TransformerUtil.transformProgram(prog, new EnumRemoverFactory());

			//fill initializers
			TransformerUtil.transformProgram(prog, new BlockModifierFactory() {

				@Override
				public void popModifierStack() {
				}

				@Override
				public BlockModifier createModifier(FunctionDefinition def) {
					return new InitializerFillModifier();
				}
			});

			//unfold local initializers
			TransformerUtil.transformProgram(prog, new BlockModifierFactory() {

				int depth = 0;

				@Override
				public void popModifierStack() {
					depth --;
				}

				@Override
				public BlockModifier createModifier(FunctionDefinition def) {
					if(depth++>0)
						return new InitializerModifier();
					else
						return new EmptyBlockModifier();
				}
			});

			TransformerUtil.transformProgram(prog, new BlockModifierFactory() {

				@Override
				public void popModifierStack() {
				}

				@Override
				public BlockModifier createModifier(FunctionDefinition def) {
					return new ArraySizeInitializerModifier();
				}
			});

			//link types
			TransformerUtil.transformProgram(prog, new TypeCompletenessFactory());

			//resolve types
			TypeResolverFactory fac = new TypeResolverFactory();
			TransformerUtil.transformProgram(prog, fac);

			//fix returns
			TransformerUtil.transformProgram(prog, new ReturnModifierFactory(fac.getResultMapping()));

			wr = new OutputStreamWriter(new FileOutputStream(new File(args[1])));

			//change function definitions to have pointers as parameters instead of arrays and structs
			TransformerUtil.transformProgram(prog, new BlockModifierFactory() {

				@Override
				public void popModifierStack() {
				}

				@Override
				public BlockModifier createModifier(FunctionDefinition def) {
					return new FunctionParameterModifier();
				}
			});

			//generate code
			MainCodeGenVisitor cg = new MainCodeGenVisitor(wr, fac.getResultMapping());
			for(InBlock ib:prog.declarations.inBlock) {
				try {
					ib.accept(cg);
				}
				catch(SemanticException ex) {
					System.err.println(ex.getMessage(GlobalPositionTracker.pos));
					GlobalErrorCounter.errors++;
				}
				catch(NullPointerException ex){
					GlobalErrorCounter.errors++;
				}
			}
			cg.finalize();
			if(GlobalErrorCounter.errors==0)
				System.out.println("Success");
		}
		catch(SemanticException ex) {
			System.err.println(ex.getMessage(GlobalPositionTracker.pos));
			GlobalErrorCounter.errors++;
		}
		finally {
			if(wr!=null) 
				wr.close();
		}
		System.exit(GlobalErrorCounter.errors);

	}
}
