package expression.constant;

import expression.Expression;
import expression.ExpressionVisitor;

public class StringConstantExpression implements Expression{
	
	public String value;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
