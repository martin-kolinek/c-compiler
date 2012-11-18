package expression;

public class TernaryExpression implements Expression {

	public Expression condition;
	public Expression ontrue;
	public Expression onfalse;
	
	public TernaryExpression(Expression cond, Expression tr, Expression fa){
		condition=cond;
		ontrue=tr;
		onfalse=fa;
	}
	
	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
