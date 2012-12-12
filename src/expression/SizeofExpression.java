package expression;

public class SizeofExpression implements Expression{
	
	public Expression exp;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}