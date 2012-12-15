package declaration.specifiers;

import astnode.ASTNode;


public enum PrimitiveTypeSpecifier implements DeclarationSpecifier, ASTNode {
	INT, LONG, FLOAT, DOUBLE, SHORT, CHAR, VOID, UNSIGNED, SIGNED;

	@Override
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);
	}
}
