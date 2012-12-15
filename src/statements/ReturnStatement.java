package statements;

import astnode.ASTNode;
import expression.Expression;

public class ReturnStatement extends Statement implements ASTNode {
	
	public Expression exp;

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
