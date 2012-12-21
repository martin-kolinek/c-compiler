package expression.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import types.PrimitiveType;

import astnode.ASTNode;
import expression.ExpressionVisitor;

public class FloatConstantExpression implements ConstantExpression, ASTNode{
	
	public types.PrimitiveType typ;
	public double value;
	
	public FloatConstantExpression(String val){
		val=val.toUpperCase();
		Pattern p = Pattern.compile("([^FL]+)(F|L)?");
		Matcher m = p.matcher(val);
		boolean match = m.matches();
		assert match;
		typ=PrimitiveType.DOUBLE;
		if(m.groupCount()>2 && m.group(2)=="F")
			typ=PrimitiveType.FLOAT;
		assert m.groupCount()>=2;
		value = Double.parseDouble(m.group(1));
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
