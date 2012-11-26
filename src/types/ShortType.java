package types;

public class ShortType implements Type {

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}
}
