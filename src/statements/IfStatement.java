package statements;

import astnode.ASTNode;
import expression.Expression;

public class IfStatement extends Statement implements ASTNode{
	
	public Expression cond;
	public Statement ontrue;
	public Statement onfalse;

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
