package declaration.specifiers;


public interface DeclarationSpecifierVisitor {

	void visit(PrimitiveTypeSpecifier primitiveTypeSpecifier);

	void visit(TypeQualifier typeQualifier);

	void visit(StorageClassSpecifier storageClassSpecifier);

	void visit(StructSpecifier structSpecifier);

	void visit(EnumSpecifier enumSpecifier);

	void visit(IDDeclarationSpecifier idDeclarationSpecifier);

}
