package declaration.declarator;

import java.util.ArrayList;

import astnode.ASTNode;

import declaration.Declaration;

public class FunctionDeclarator implements Declarator, ASTNode {

	public FunctionDeclarator(Declarator d){
		parameters=new ArrayList<Declaration>();
		declarator=d;
		variadic = false;
	}
	
	public Declarator declarator;
	
	public ArrayList<Declaration> parameters;
	
	@Override
	public void accept(DeclaratorVisitor v) {
		v.visit(this);
	}
	
	//... v deklaracii funkcie
	public boolean variadic; 

}
