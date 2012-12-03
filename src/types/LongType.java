package types;

public class LongType extends Type {

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this); 
	}
	
}
