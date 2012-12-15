package expression.constant;

import astnode.ASTNode;
import expression.ExpressionVisitor;

public class FloatConstantExpression implements ConstantExpression, ASTNode{
	
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
