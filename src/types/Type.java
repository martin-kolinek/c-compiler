package types;

public abstract class Type {
	public StorageClassSpecifier stClass;
	public boolean constant;
	public abstract void accept(TypeVisitor v);
}
