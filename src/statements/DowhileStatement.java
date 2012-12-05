package statements;

import expression.Expression;

public class DowhileStatement extends Statement {
	
	public Statement body;
	public Expression condition;

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
