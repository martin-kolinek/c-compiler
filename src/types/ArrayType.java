package types;

import toplevel.InBlockVisitor;
import expression.Expression;

public class ArrayType implements Type {

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

	@Override
	public void accept(InBlockVisitor v) {
		v.visit(this);
	}

}
