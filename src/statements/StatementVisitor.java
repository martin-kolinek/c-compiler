package statements;

public interface StatementVisitor {

	void visit(ReturnStatement returnStatement);

	void visit(BreakStatement breakStatement);

	void visit(ContinueStatement continueStatement);

	void visit(DowhileStatement dowhileStatement);

	void visit(ForStatement forStatement);

	void visit(WhileStatement whileStatement);

	void visit(SwitchStatement switchStatement);

	void visit(IfStatement ifStatement);

}
