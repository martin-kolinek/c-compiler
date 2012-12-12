package expression;

public class IDExpression implements Expression {

	public IDExpression(String id) {
		this.id=id;
	}
	
	public String id;
	
	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
