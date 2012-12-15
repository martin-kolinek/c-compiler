package declaration.specifiers;

import astnode.ASTNode;

public class TypedefDeclarationSpecifier implements DeclarationSpecifier, ASTNode {

	@Override
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);
	}

}
