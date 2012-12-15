package expression;

import astnode.ASTNode;

public class IDExpression implements Expression, ASTNode {

	public IDExpression(String id) {
		this.id=id;
	}
	
	public String id;
	
	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
