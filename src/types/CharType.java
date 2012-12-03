package types;

public class CharType extends Type {

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}
}
