package types;

public class IntType implements Type {

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}
}
