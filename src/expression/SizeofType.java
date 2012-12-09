package expression;

import toplevel.InBlock;

public class SizeofType implements Expression{
	
	public InBlock typedecl; //this can be declaration or a type

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}