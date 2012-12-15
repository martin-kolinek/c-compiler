package statements;

import astnode.ASTNode;

public class BreakStatement extends Statement implements ASTNode {
	
	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
