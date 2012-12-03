package types;

public class IntType extends Type {

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}
}
