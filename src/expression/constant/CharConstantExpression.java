package expression.constant;

import astnode.ASTNode;
import expression.ExpressionVisitor;

public class CharConstantExpression implements ConstantExpression, ASTNode{
	
	public char value;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

	@Override
	public Float getNumericValue() {
		return new Float(Character.getNumericValue(value));
	}

}
