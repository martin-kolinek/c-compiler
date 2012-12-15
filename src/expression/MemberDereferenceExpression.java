package expression;

import astnode.ASTNode;

public class MemberDereferenceExpression implements Expression, ASTNode{
	
	public Expression exp;
	public String id;
	
	public MemberDereferenceExpression(Expression ex, String id){
		this.id=id;
		this.exp=ex;
	}

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}