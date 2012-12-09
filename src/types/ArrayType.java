package types;

import expression.Expression;

public class ArrayType implements Type {

	public ArrayType(Type t, Expression e) {
		size=e;
		elementType=t;
	}
	
	private Expression size;
	
	public Expression getSizeExpression() {
		return size;
	}
	
	private Type elementType;
	
	public Type getElementType() {
		return elementType;
	}
	
	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}

}
