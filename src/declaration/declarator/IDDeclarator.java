package declaration.declarator;

import astnode.ASTNode;

public class IDDeclarator implements Declarator, ASTNode {

	public IDDeclarator(String id) {
		this.id=id;
	}
	
	public String id;
	
	@Override
	public void accept(DeclaratorVisitor v) {
		v.visit(this);
	}

}
