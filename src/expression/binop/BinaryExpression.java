package expression.binop;

import expression.Expression;
import expression.ExpressionVisitor;

public abstract class BinaryExpression implements Expression {
	
	public Expression left;
	public Expression right;
	
	@Override
	public abstract void accept(ExpressionVisitor v);
}
