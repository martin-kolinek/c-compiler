package expression;

public class MemberDereferenceExpression implements Expression{
	
	public Expression exp;
	public String id;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}