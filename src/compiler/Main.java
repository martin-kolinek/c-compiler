package compiler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import modifiers.AssignmentModifier;
import modifiers.CommaExpressionModifier;
import modifiers.FunctionParameterModifier;
import modifiers.LoopModifier;
import modifiers.PointerModifier;
import modifiers.UnaryChargeModifier;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;

import codegen.MainCodeGenVisitor;

import declaration.DeclarationResolver;

import toplevel.FunctionDefinition;
import toplevel.InBlock;
import toplevel.Program;
import transformers.BlockModifier;
import transformers.BlockModifierFactory;
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
		
		//link types
		TransformerUtil.transformProgram(prog, new TypeCompletenessFactory());
				
		//resolve types
		TypeResolverFactory fac = new TypeResolverFactory();
		TransformerUtil.transformProgram(prog, fac);
		
		OutputStreamWriter wr = new OutputStreamWriter(new FileOutputStream(new File(args[1])));
		
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
		for(InBlock ib:prog.declarations.inBlock)
			ib.accept(cg);
		
		System.out.println("Done");
	}
}
