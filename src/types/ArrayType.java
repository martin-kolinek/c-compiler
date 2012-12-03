package types;

import expression.Expression;

public class ArrayType extends Type {

	private Expression size;
	
	public Expression getSizeExpression() {
		return size;
	}
	
	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}

}
