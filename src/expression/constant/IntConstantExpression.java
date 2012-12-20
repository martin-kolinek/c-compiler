package expression.constant;

import astnode.ASTNode;
import expression.ExpressionVisitor;

public class IntConstantExpression implements ConstantExpression, ASTNode{
	
	public IntConstantExpression(String val){
		if (val.matches("([0-9]+)LL")){
			//long
			typ = types.PrimitiveType.LONG;
			value = Long.parseLong(val.replace("LL", ""));
		} else if (val.matches("([0-9]+)u")){
			//uint
			typ = types.PrimitiveType.UINT;
			value = Long.parseLong(val.replace("u", ""));
		} else if (val.matches("\\'(.)\\'")){
			//char
			typ = types.PrimitiveType.CHAR;
			value = val.charAt(1);
		} else {
			//int
			typ = types.PrimitiveType.INT;
			value = Long.parseLong(val);
		}
	}
	
	public IntConstantExpression(){
		typ = types.PrimitiveType.LONG;
	}
	
	public IntConstantExpression(long val) {
		typ = types.PrimitiveType.LONG;
		value=val;
	}
	
	public IntConstantExpression(double val) {
		typ = types.PrimitiveType.LONG;
		value=(long)val;
	}
	
	public types.PrimitiveType typ;
	public long value;

	@Override
	public void accept(ExpressionVisitor v) {
		typ = types.PrimitiveType.LONG;
		v.visit(this);
	}

	@Override
	public double getNumericValue() {
		return new Float(value);
	}

}
