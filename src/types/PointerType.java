package types;

public class PointerType implements Type {

	public PointerType(Type t) {
		pointedToType=t;
	}
	
	public final Type pointedToType;

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}

}