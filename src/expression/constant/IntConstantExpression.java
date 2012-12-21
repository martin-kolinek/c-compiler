package expression.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import types.PrimitiveType;

import astnode.ASTNode;
import expression.ExpressionVisitor;

public class IntConstantExpression implements ConstantExpression, ASTNode{
	
	public IntConstantExpression(String val){
		Pattern p1 = Pattern.compile("([0-9]+)(\\w*)");
		Pattern p2 = Pattern.compile("\\'(.)\\'");
		typ = PrimitiveType.INT;
		Matcher m1 = p1.matcher(val);
		if(m1.matches()) {
			value = Long.parseLong(m1.group(1));
			String suffix = m1.group(2).toUpperCase();
			if(suffix == "U")
				typ=PrimitiveType.UINT;
			else if(suffix == "ULL")
				typ=PrimitiveType.ULONG;
			else if(suffix == "LL")
				typ=PrimitiveType.LONG;
			else if(suffix == "US")
				typ=PrimitiveType.USHORT;
			else if(suffix == "S")
				typ=PrimitiveType.SHORT;
			else
				typ=PrimitiveType.INT;
		}
		Matcher m2 = p2.matcher(val);
		if(m2.matches()) {
			typ = types.PrimitiveType.UCHAR;
			value = m2.group(1).charAt(0);
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
