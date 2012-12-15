package statements;

import astnode.ASTNode;
import expression.Expression;

public class WhileStatement extends Statement implements ASTNode{

	public WhileStatement(){
	}
	
	public WhileStatement(Expression cond, Statement b) {
		condition=cond;
		body=b;
	}
	
	public Expression condition;
	public Statement body;

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
