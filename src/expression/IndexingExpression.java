package expression;

public class IndexingExpression implements Expression {

	public IndexingExpression(Expression target, Expression index){
		this.target=target;
		this.index=index;
	}
	
	Expression index;
	Expression target;
	
	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
