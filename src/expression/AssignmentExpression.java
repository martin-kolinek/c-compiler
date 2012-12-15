package expression;

import astnode.ASTNode;

public class AssignmentExpression implements Expression, ASTNode {

	public Expression left, right;
	
	public AssignmentExpression(Expression l, Expression r) {
		left = l;
		right = r;
	}
	
	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
