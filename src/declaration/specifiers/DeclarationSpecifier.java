package declaration.specifiers;

import astnode.ASTNode;

public interface DeclarationSpecifier extends ASTNode {
	void accept(DeclarationSpecifierVisitor v);
}
