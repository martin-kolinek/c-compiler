package expression.binop;

import astnode.ASTNode;
import expression.Expression;
import expression.ExpressionVisitor;

public class BinaryExpression implements Expression, ASTNode {
	
	public BinaryExpression(Expression e1, BinaryOperator op, Expression e2) {
		left=e1;
		right=e2;
		operator=op;
	}
	
	public BinaryOperator operator;
	public Expression left;
	public Expression right;
	
	@Override
	public void accept(ExpressionVisitor v){
		v.visit(this);
	}
}
