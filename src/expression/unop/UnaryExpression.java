package expression.unop;

import expression.Expression;
import expression.ExpressionVisitor;

public class UnaryExpression implements Expression{
	
	public UnaryExpression() {
	}
	
	public UnaryExpression(Expression exp, UnaryOperator op) {
		this.exp=exp;
		this.op=op;
	}
	
	public Expression exp;
	public UnaryOperator op;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
