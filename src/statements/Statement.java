package statements;

public interface Statement {
	void accept(StatementVisitor v);
}
