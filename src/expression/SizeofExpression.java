package expression;

import astnode.ASTNode;

public class SizeofExpression implements Expression, ASTNode{
	
	public Expression exp;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}