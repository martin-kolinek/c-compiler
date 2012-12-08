package expression.constant;

import expression.Expression;
import expression.ExpressionVisitor;

public class FloatConstantExpression implements Expression{
	
	public Float value;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
