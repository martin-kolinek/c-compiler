package statements;

public class ContinueStatement extends Statement {
	
	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
