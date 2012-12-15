package statements;

import astnode.ASTNode;
import expression.Expression;

public class DowhileStatement extends Statement implements ASTNode{
	
	public Statement body;
	public Expression condition;

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
