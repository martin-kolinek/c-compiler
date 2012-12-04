package expression.binop;

import expression.Expression;
import expression.ExpressionVisitor;

public class BinaryExpression implements Expression {
	public BinaryOperator operator;
	public Expression left;
	public Expression right;
	
	@Override
	public void accept(ExpressionVisitor v){
		v.visit(this);
	}
}
