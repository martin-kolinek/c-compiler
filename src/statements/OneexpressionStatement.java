package statements;

import astnode.ASTNode;
import expression.Expression;

public class OneexpressionStatement extends Statement implements ASTNode{

	public OneexpressionStatement() {
		
	}
	
	public OneexpressionStatement(Expression e){
		expr=e;
	}
	
	public Expression expr;

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
