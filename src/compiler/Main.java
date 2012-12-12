package compiler;

import modifiers.AssignmentModifier;
import modifiers.CommaExpressionModifier;
import modifiers.LoopModifier;
import modifiers.PointerModifier;
import modifiers.UnaryChargeModifier;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;

import declaration.DeclarationResolver;

import toplevel.Program;
import transformers.BlockModifier;
import transformers.BlockModifierFactory;
import transformers.ExpressionModifier;
import transformers.ExpressionModifierFactory;
import transformers.StatementModifier;
import transformers.StatementModifierFactory;
import transformers.TransformerUtil;
import types.TypeCompletenessFactory;
import types.TypedefRemoverFactory;

import grammar.generated.cgrammarLexer;
import grammar.generated.cgrammarParser;

public class Main {
	public static void main(String[] args) throws Exception {
		if(args.length!=1) {
			System.out.println("Expecting file name");
			System.exit(1);
		}
		System.out.println(new java.io.File( "." ).getCanonicalPath());
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
			public BlockModifier createModifier() {
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
		
		//link types
		TransformerUtil.transformProgram(prog, new TypeCompletenessFactory());
		
		System.out.println(prog.declarations.inBlock.size());
	}
}
