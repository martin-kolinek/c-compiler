package expression;

public class FunctionCallExpression implements Expression {

	public String name;
	public Expression args;
	
	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}