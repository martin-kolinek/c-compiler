package declaration;

import java.util.ArrayList;

import declaration.declarator.Declarator;
import declaration.specifiers.DeclarationSpecifier;

public class Declaration {

	public Declaration() {
		declSpecs=new ArrayList<DeclarationSpecifier>();
		declarators=new ArrayList<InitDeclarator>();
	}
	public ArrayList<DeclarationSpecifier> declSpecs;
	public ArrayList<InitDeclarator> declarators;
	
	public void addDeclarator(Declarator decl){
		declarators.add(new InitDeclarator(decl));
	}

}
