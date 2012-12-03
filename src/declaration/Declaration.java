package declaration;

import java.util.ArrayList;

import declaration.declarator.Declarator;
import declaration.specifiers.DeclarationSpecifier;

public class Declaration {

	public Declaration() {
		declSpecs=new ArrayList<DeclarationSpecifier>();
	}
	public ArrayList<DeclarationSpecifier> declSpecs;
	public ArrayList<Declarator> declarator;

}
