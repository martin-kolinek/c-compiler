package modifiers;

import expression.Expression;
import expression.constant.IntConstantExpression;
import statements.BlockStatement;
import statements.ForStatement;
import statements.OneexpressionStatement;
import statements.WhileStatement;
import transformers.EmptyStatementModifier;

/**
 * Toto by sa malo spustit pred riesenim deklaracii, kvoli deklaracii vo fore, ktoru tymto presunieme do bloku
 * @author martin
 *
 */
public class LoopModifier extends EmptyStatementModifier {
	
	/*
	 * Transformuje for(aaa; bbb; ccc) ddd
	 * na
	 * aaa;
	 * while(bbb)
	 * {
	 *    ddd;
	 *    ccc;
	 * }
	 */
	@Override
	public void visit(ForStatement s) {
		BlockStatement st = new BlockStatement();
		if(s.decl!=null)
			st.inBlock.add(s.decl);
		if(s.init!=null)
			st.inBlock.add(new OneexpressionStatement(s.init));
		Expression e = s.cond;
		if(e==null)
			e=new IntConstantExpression(1);
		BlockStatement body = new BlockStatement();
		body.inBlock.add(s.body);
		if(s.after!=null)
			body.inBlock.add(new OneexpressionStatement(s.after));
		st.inBlock.add(new WhileStatement(e, body));
		result = st;
	}
	
}
