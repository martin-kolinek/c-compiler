package declaration.specifiers;

public enum StorageClassSpecifier implements DeclarationSpecifier {
	STATIC, EXTERN, AUTO, REGISTER;
	@Override
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);
	}

}
