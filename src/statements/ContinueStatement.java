package statements;

public class ContinueStatement implements Statement {
	
	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
