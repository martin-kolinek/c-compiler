package block;

import declaration.Declaration;
import statements.Statement;

public interface InBlockVisitor {

	void visit(Statement statement);

	void visit(Declaration declaration);
	
}
