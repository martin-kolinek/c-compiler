package expression.constant;

import expression.ExpressionVisitor;

public class FloatConstantExpression implements ConstantExpression{
	
	public float value;
	
	public FloatConstantExpression(){
		
	}
	
	public FloatConstantExpression(Float val){
		value = val;
	}

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

	@Override
	public Float getNumericValue() {
		return value;
	}

}
