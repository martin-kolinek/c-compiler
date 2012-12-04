package statements;

public class BreakStatement implements Statement {
	
	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
