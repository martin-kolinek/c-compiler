package types;

import toplevel.InBlockVisitor;

public class PointerType implements Type {

	public PointerType(Type t) {
		pointedToType=t;
	}
	
	public final Type pointedToType;
	
	@Override
	public void accept(InBlockVisitor v) {
		v.visit(this);
	}

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}

}
