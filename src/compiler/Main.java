package compiler;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;

import declaration.DeclarationResolver;

import toplevel.Program;
import transformers.BlockModifier;
import transformers.BlockModifierFactory;
import transformers.BlockTransformer;
import transformers.LoopModifier;
import transformers.StatementModifier;
import transformers.StatementModifierFactory;
import transformers.TransformerUtil;

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
		
		
		System.out.println(prog.declarations.inBlock.size());
	}
}
