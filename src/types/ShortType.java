package types;

public class ShortType extends Type {

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}
}
