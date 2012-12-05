package statements;

import expression.Expression;

public class OneexpressionStatement implements Statement{
	
	public Expression exp;

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
