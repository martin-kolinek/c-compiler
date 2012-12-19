package expression.constant;

import astnode.ASTNode;
import expression.ExpressionVisitor;

public class FloatConstantExpression implements ConstantExpression, ASTNode{
	
	public double value;
	
	public FloatConstantExpression(){
		
	}
	
	public FloatConstantExpression(double val){
		value = val;
	}

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

	@Override
	public double getNumericValue() {
		return value;
	}

}
