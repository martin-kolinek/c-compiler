package declaration.specifiers;

import java.util.ArrayList;

import declaration.Declaration;

public class StructSpecifier implements DeclarationSpecifier {

	public StructSpecifier(String tag){
		this.tag=tag;
		memberDecls = new ArrayList<Declaration>();
	}
	
	public String tag;
	
	public ArrayList<Declaration> memberDecls;
	
	@Override
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);
	}

}
