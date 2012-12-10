package toplevel;

import declaration.Declaration;
import declaration.ResolvedDeclaration;
import declaration.TypedefDeclaration;
import statements.Statement;

public interface InBlockVisitor {

	void visit(Statement i);

	void visit(Declaration i);

	void visit(FunctionDefinition i);

	void visit(TypedefDeclaration i);

	void visit(ResolvedDeclaration i);
	
}
