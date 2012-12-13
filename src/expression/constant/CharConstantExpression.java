package expression.constant;

import expression.ExpressionVisitor;

public class CharConstantExpression implements ConstantExpression{
	
	public char value;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

	@Override
	public Float getNumericValue() {
		return new Float(Character.getNumericValue(value));
	}

}
