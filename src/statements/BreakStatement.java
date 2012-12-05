package statements;

public class BreakStatement extends Statement {
	
	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
