package expression;

import java.util.ArrayList;

public class FunctionCallExpression implements Expression {

	public FunctionCallExpression(String name) {
		args=new ArrayList<Expression>();
		this.name=name;
	}
	
	public String name;
	public ArrayList<Expression> args;
	
	public void addExp(Expression exp){
		args.add(exp);
	}
	
	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}