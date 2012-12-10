package types;

public interface TypeVisitor {

	void visit(StructType t);

	void visit(ArrayType t);

	void visit(TypedefType t);

	void visit(PrimitiveType t);

	void visit(EnumType t);
	
	void visit(PointerType t);

}
