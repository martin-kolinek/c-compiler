package types;

import astnode.ASTNode;
import expression.Expression;

public class ArrayType implements Type, ASTNode {

	public ArrayType(Type t, Expression e) {
		size=e;
		elementType=t;
	}
	
	public Expression size;
	
	public Expression getSizeExpression() {
		return size;
	}
	
	public Type elementType;
	
	public Type getElementType() {
		return elementType;
	}
	
	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}

}
