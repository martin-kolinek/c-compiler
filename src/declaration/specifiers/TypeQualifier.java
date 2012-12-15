package declaration.specifiers;

import astnode.ASTNode;

public enum TypeQualifier implements DeclarationSpecifier, ASTNode {
	CONST, RESTRICT, VOLATILE;
	
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);

	}

}
