package declaration.declarator;

import astnode.ASTNode;

public interface Declarator extends ASTNode {
	void accept(DeclaratorVisitor v);
}
