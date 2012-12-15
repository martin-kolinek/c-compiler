package declaration;

import java.util.ArrayList;

import astnode.ASTNode;

import toplevel.InBlock;
import toplevel.InBlockVisitor;


import declaration.declarator.Declarator;
import declaration.specifiers.DeclarationSpecifier;

public class Declaration implements InBlock, ASTNode {

	public Declaration() {
		declSpecs=new ArrayList<DeclarationSpecifier>();
		declarators=new ArrayList<InitDeclarator>();
	}
	public Declaration(ArrayList<DeclarationSpecifier> ds) {
		declSpecs=ds;
		declarators = new ArrayList<InitDeclarator>();
	}
	public ArrayList<DeclarationSpecifier> declSpecs;
	public ArrayList<InitDeclarator> declarators;
	
	public void addDeclarator(Declarator decl){
		declarators.add(new InitDeclarator(decl));
	}

	@Override
	public void accept(InBlockVisitor v) {
		v.visit(this);
	}

}
