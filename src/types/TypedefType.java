package types;

public class TypedefType extends Type {

	public TypedefType(String id) {
		//TODO
	}

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}

}
