package types;

import astnode.ASTNode;

public class TypedefType implements Type, ASTNode {
	
	public String identifier;
	
	public TypedefType(String id) {
		identifier = id;
	}

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}
}
