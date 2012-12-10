package toplevel;

import declaration.Declaration;
import declaration.ResolvedDeclaration;
import declaration.TypedefDeclaration;
import statements.Statement;

public interface InBlockVisitor {

	void visit(Statement statement);

	void visit(Declaration declaration);

	void visit(FunctionDefinition functionDefinition);

	void visit(TypedefDeclaration typedefDeclaration);

	void visit(ResolvedDeclaration resolvedDeclaration);
	
}
