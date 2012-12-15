package declaration.specifiers;

import astnode.ASTNode;

public class IDDeclarationSpecifier implements DeclarationSpecifier, ASTNode {

	public String id;
	
	public IDDeclarationSpecifier(String id){
		this.id=id;
	}
	
	@Override
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);
	}

}
