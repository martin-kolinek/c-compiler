package compiler;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;

import declaration.DeclarationResolver;

import toplevel.Program;
import transformers.AssignmentModifier;
import transformers.BlockModifier;
import transformers.BlockModifierFactory;
import transformers.BlockTransformer;
import transformers.CommaExpressionModifier;
import transformers.ExpressionModifier;
import transformers.ExpressionModifierFactory;
import transformers.LoopModifier;
import transformers.PointerModifier;
import transformers.StatementModifier;
import transformers.StatementModifierFactory;
import transformers.TransformerUtil;
import transformers.UnaryChargeModifier;
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
		BlockTransformer loops = TransformerUtil.blockTranForStatementModifier(new StatementModifierFactory() {
			@Override
			public StatementModifier create() {
				return new LoopModifier();
			}
		});
		prog.declarations.accept(loops);
		
		//resolve declarations
		BlockTransformer decls = new BlockTransformer(new BlockModifierFactory() {
			@Override
			public void popModifierStack() {
			}
			@Override
			public BlockModifier createModifier() {
				return new DeclarationResolver();
			}
		});
		prog.declarations.accept(decls);
		
		//remove single expression comma expressions
		BlockTransformer comma = TransformerUtil.blockTranForExpressionModifier(new ExpressionModifierFactory() {
			
			@Override
			public ExpressionModifier create() {
				return new CommaExpressionModifier();
			}
		});
		prog.declarations.accept(comma);
		
		//remove [] and -> expressions
		BlockTransformer deref = TransformerUtil.blockTranForExpressionModifier(new ExpressionModifierFactory() {
			
			@Override
			public ExpressionModifier create() {
				return new PointerModifier();
			}
		});
		prog.declarations.accept(deref);
		
		//remove compound assignments
		BlockTransformer compound = TransformerUtil.blockTranForExpressionModifier(new ExpressionModifierFactory() {
			
			@Override
			public ExpressionModifier create() {
				return new AssignmentModifier();
			}
		});
		prog.declarations.accept(compound);
		
		//remove unary +,- operators
		BlockTransformer charge = TransformerUtil.blockTranForExpressionModifier(new ExpressionModifierFactory() {
			
			@Override
			public ExpressionModifier create() {
				return new UnaryChargeModifier();
			}
		});
		prog.declarations.accept(charge);

		//remove typedefs
		BlockTransformer typedef = new BlockTransformer(new TypedefRemoverFactory());
		prog.declarations.accept(typedef);
		
		System.out.println(prog.declarations.inBlock.size());
	}
}
