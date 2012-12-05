package expression;

import declaration.Declaration;

public class SizeofType implements Expression{
	
	public Declaration typedecl;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}