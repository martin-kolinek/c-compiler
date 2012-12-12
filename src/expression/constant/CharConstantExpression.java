package expression.constant;

import expression.Expression;
import expression.ExpressionVisitor;

public class CharConstantExpression implements Expression{
	
	public char value;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
