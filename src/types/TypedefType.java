package types;

public class TypedefType implements Type {
	
	public TypedefType(String id) {
		//TODO
	}

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}
}
