package declaration.specifiers;

import astnode.ASTNode;

public enum StorageClassSpecifier implements DeclarationSpecifier, ASTNode {
	STATIC, EXTERN, AUTO, REGISTER;
	@Override
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);
	}

}
