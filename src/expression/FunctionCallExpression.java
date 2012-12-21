package expression;

import java.util.ArrayList;

import toplevel.FunctionDefinition;

import astnode.ASTNode;

public class FunctionCallExpression implements Expression, ASTNode {

	public FunctionCallExpression(String name) {
		args=new ArrayList<Expression>();
		this.name=name;
	}
	
	public String name;
	public ArrayList<Expression> args;
	public FunctionDefinition func;
	
	public void addExp(Expression exp){
		args.add(exp);
	}
	
	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}