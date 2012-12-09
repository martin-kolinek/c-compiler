package toplevel;

import declaration.Declaration;
import statements.Statement;
import types.Type;

public interface InBlockVisitor {

	void visit(Statement statement);

	void visit(Declaration declaration);

	void visit(FunctionDefinition functionDefinition);

	void visit(Type type);
	
}
