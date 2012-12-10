package statements;

public interface StatementVisitor {

	void visit(ReturnStatement s);

	void visit(BreakStatement s);

	void visit(ContinueStatement s);

	void visit(DowhileStatement s);

	void visit(ForStatement s);

	void visit(WhileStatement s);

	void visit(SwitchStatement s);

	void visit(IfStatement s);

	void visit(OneexpressionStatement s);

	void visit(BlockStatement s);

}
