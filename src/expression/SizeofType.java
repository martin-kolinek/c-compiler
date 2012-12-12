package expression;

import declaration.Declaration;
import types.Type;

public class SizeofType implements Expression{
	
	public Declaration typedecl;
	
	public Type type;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}