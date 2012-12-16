package codegen;

public interface CodeGenFactory {
	CodeGenExpressionVisitor createExpVis();
	CodeGenStatementVisitor createStmtVis();
}
