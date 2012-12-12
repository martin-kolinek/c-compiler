package expression;

import types.Type;
import declaration.Declaration;

public class CastExpression implements Expression{
	
	public CastExpression(){
	}
	
	public CastExpression(Expression e, Type t){
		exp=e;
		type=t;
	}
	
	public Declaration typedecl;
	public Expression exp;
	public Type type;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
