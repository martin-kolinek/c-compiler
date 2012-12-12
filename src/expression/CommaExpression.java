package expression;

import java.util.ArrayList;

public class CommaExpression implements Expression {

	public CommaExpression(Expression e){
		expressions=new ArrayList<Expression>();
		expressions.add(e);
	}
	
	public ArrayList<Expression> expressions;
	
	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
