package expression.unop;

import expression.Expression;
import expression.ExpressionVisitor;

public class UnaryExpression implements Expression{
	
	public Expression exp;
	public UnaryOperator op;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
