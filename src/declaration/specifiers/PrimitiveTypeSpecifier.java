package declaration.specifiers;


public enum PrimitiveTypeSpecifier implements DeclarationSpecifier {
	INT, LONG, FLOAT, DOUBLE, SHORT, CHAR, VOID, UNSIGNED, SIGNED;

	@Override
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);
	}
}
