package statements;

import expression.Expression;

public class WhileStatement implements Statement {
	
	public Expression condition;
	public Statement body;

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
