package declaration.specifiers;

public class IDDeclarationSpecifier implements DeclarationSpecifier {

	public String id;
	
	public IDDeclarationSpecifier(String id){
		this.id=id;
	}
	
	@Override
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);
	}

}
