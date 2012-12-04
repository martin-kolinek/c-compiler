package statements;

import expression.Expression;

public class IfStatement implements Statement{
	
	public Expression cond;
	public Statement ontrue;
	public Statement onfalse;

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
