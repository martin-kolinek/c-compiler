package toplevel;

import declaration.Declaration;
import declaration.ResolvedDeclaration;
import declaration.TypedefDeclaration;
import statements.Statement;
import types.Type;

public interface InBlockVisitor {

	void visit(Statement statement);

	void visit(Declaration declaration);

	void visit(FunctionDefinition functionDefinition);

	void visit(Type type);

	void visit(TypedefDeclaration typedefDeclaration);

	void visit(ResolvedDeclaration resolvedDeclaration);
	
}
