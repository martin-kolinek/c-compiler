package types;

public class LongType implements Type {

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this); 
	}
	
}
