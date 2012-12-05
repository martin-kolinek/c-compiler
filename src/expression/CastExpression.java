package expression;

import declaration.Declaration;

public class CastExpression implements Expression{
	
	public Declaration typedecl;
	public Expression exp;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
