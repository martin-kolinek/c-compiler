package toplevel;

import declaration.Declaration;
import statements.Statement;

public interface InBlockVisitor {

	void visit(Statement statement);

	void visit(Declaration declaration);

	void visit(FunctionDefinition functionDefinition);
	
}
