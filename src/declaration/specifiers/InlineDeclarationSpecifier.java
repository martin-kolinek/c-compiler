package declaration.specifiers;

public class InlineDeclarationSpecifier implements DeclarationSpecifier {

	@Override
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);
	}

}
