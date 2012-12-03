package declaration.declarator;

public class IDDeclarator implements Declarator {

	public IDDeclarator(String id) {
		this.id=id;
	}
	
	public String id;
	
	@Override
	public void accept(DeclaratorVisitor v) {
		v.visit(this);
	}

}
