package declaration.specifiers;

import astnode.ASTNode;

public class InlineDeclarationSpecifier implements DeclarationSpecifier, ASTNode {

	@Override
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);
	}

}
