package declaration.specifiers;

public enum TypeQualifier implements DeclarationSpecifier {
	CONST, RESTRICT, VOLATILE;
	
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);

	}

}
