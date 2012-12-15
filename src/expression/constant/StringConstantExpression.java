package expression.constant;

import astnode.ASTNode;
import expression.Expression;
import expression.ExpressionVisitor;

public class StringConstantExpression implements Expression, ASTNode{
	
	public String value; //this is raw string read from source

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
