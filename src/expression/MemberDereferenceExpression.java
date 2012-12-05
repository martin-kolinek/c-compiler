package expression;

public class MemberDereferenceExpression implements Expression{
	
	public Expression exp;
	public String id;
	
	public MemberDereferenceExpression(Expression ex, String id){
		this.id=id;
		this.exp=ex;
	}

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}