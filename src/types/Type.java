package types;

public interface Type {
	void accept(TypeVisitor v);
	
}
