package statements;

import expression.Expression;

public class ReturnStatement extends Statement {
	
	public Expression exp;

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
