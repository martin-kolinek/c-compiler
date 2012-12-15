package types;

import astnode.ASTNode;

public class PointerType implements Type, ASTNode {

	public PointerType(Type t) {
		pointedToType=t;
	}
	
	public Type pointedToType;

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}

}
