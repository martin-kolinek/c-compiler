package expression.constant;

import expression.ExpressionVisitor;

public class IntConstantExpression implements ConstantExpression{
	
	public IntConstantExpression(){
	}
	
	public IntConstantExpression(int val) {
		value=val;
	}
	
	public IntConstantExpression(Float val) {
		value=Math.round(val);
	}
	
	public int value;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

	@Override
	public Float getNumericValue() {
		return new Float(value);
	}

}
