package declaration.specifiers;

import java.util.ArrayList;

import astnode.ASTNode;

import declaration.Declaration;

public class StructSpecifier implements DeclarationSpecifier, ASTNode {

	public StructSpecifier(String tag){
		this.tag=tag;
		memberDecls = null;
	}
	
	public StructSpecifier() {
		tag=null;
		memberDecls = new ArrayList<Declaration>();
	}
	
	public String tag;
	
	public ArrayList<Declaration> memberDecls;
	
	@Override
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);
	}

}
