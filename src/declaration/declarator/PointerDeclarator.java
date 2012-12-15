package declaration.declarator;

import java.util.ArrayList;

import astnode.ASTNode;

import declaration.specifiers.TypeQualifier;

public class PointerDeclarator implements Declarator, ASTNode {

	public PointerDeclarator(ArrayList<TypeQualifier> qual){
		qualifiers=qual;
	}
	
	public Declarator declarator;
	
	public ArrayList<TypeQualifier> qualifiers;
	
	@Override
	public void accept(DeclaratorVisitor v) {
		v.visit(this);
	}
	
}
