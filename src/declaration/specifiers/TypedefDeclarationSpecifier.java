package declaration.specifiers;

public class TypedefDeclarationSpecifier implements DeclarationSpecifier {

	@Override
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);
	}

}
