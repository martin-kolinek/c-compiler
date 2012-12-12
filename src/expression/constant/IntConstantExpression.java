package expression.constant;

import expression.Expression;
import expression.ExpressionVisitor;

public class IntConstantExpression implements Expression{
	
	public IntConstantExpression(){
	}
	
	public IntConstantExpression(int val) {
		value=val;
	}
	
	public int value;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
