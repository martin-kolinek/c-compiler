package types;

public class TypedefType implements Type {
	
	public String identifier;
	
	public TypedefType(String id) {
		identifier = id;
	}

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}
}
