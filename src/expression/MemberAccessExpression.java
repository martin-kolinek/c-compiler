package expression;

import astnode.ASTNode;

public class MemberAccessExpression implements Expression, ASTNode {
	
	public Expression exp;
	public String id;//TODO sem treba dat LLVM typ + index

	public MemberAccessExpression(Expression ex, String id) {
		this.id = id;
		this.exp=ex;
	}
	
	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
