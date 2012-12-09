package types;

import toplevel.InBlockVisitor;

public class TypedefType implements Type {
	
	public TypedefType(String id) {
		//TODO
	}

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}

	@Override
	public void accept(InBlockVisitor v) {
		v.visit(this);
	}

}
