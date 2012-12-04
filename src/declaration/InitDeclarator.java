package declaration;

import declaration.declarator.Declarator;
import declaration.initializer.Initializer;

public class InitDeclarator {
	public Declarator declarator;
	public InitDeclarator(Declarator decl) {
		declarator=decl;
		initializer=null;
	}
	
	public Initializer initializer;
}
