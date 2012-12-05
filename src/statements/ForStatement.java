package statements;

import declaration.Declaration;
import expression.Expression;

public class ForStatement extends Statement{
	
	// if (decl init; cond; after) body
	public Declaration decl;
	public Expression init;
	public Expression cond;
	public Expression after;
	public Statement body;

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
