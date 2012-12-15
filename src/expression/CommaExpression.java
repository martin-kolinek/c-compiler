package expression;

import java.util.ArrayList;

import astnode.ASTNode;

public class CommaExpression implements Expression, ASTNode {

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
