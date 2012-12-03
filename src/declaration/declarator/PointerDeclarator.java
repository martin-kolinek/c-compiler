package declaration.declarator;

import java.util.ArrayList;

import declaration.specifiers.TypeQualifier;

public class PointerDeclarator implements Declarator {

	public PointerDeclarator(Declarator d){
		qualifiers=new ArrayList<TypeQualifier>();
		declarator=d;
	}
	
	Declarator declarator;
	
	public ArrayList<TypeQualifier> qualifiers;
	
	@Override
	public void accept(DeclaratorVisitor v) {
		v.visit(this);
	}
	
}
