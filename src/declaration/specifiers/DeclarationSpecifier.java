package declaration.specifiers;

public interface DeclarationSpecifier {
	void accept(DeclarationSpecifierVisitor v);
}
