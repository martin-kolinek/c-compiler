package expression.constant;

import astnode.ASTNode;
import expression.ExpressionVisitor;

public class FloatConstantExpression implements ConstantExpression, ASTNode{
	
	public types.PrimitiveType typ;
	public double value;
	
	public FloatConstantExpression(String val){
		if (val.matches("^([0-9\\.]+f)$")){
			//float
			typ = types.PrimitiveType.FLOAT;
			value = Float.parseFloat(val.replaceAll("f", ""));
		} else {
			//double
			typ = types.PrimitiveType.DOUBLE;
			value = Double.parseDouble(val);
		}
	}
	
	public FloatConstantExpression(){
		typ = types.PrimitiveType.DOUBLE;
	}
	
	public FloatConstantExpression(double val){
		typ = types.PrimitiveType.DOUBLE;
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
