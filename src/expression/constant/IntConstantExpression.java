package expression.constant;

import astnode.ASTNode;
import expression.ExpressionVisitor;

public class IntConstantExpression implements ConstantExpression, ASTNode{
	
	public IntConstantExpression(){
	}
	
	public IntConstantExpression(long val) {
		value=val;
	}
	
	public IntConstantExpression(double val) {
		value=(long)val;
	}
	
	public long value;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

	@Override
	public double getNumericValue() {
		return new Float(value);
	}

}
