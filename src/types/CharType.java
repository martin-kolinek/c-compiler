package types;

public class CharType implements Type {

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}
}
