package types;

public interface TypeVisitor {

	void visit(StructType structType);

	void visit(ArrayType arrayType);

	void visit(TypedefType typedefType);

	void visit(PrimitiveType primitiveType);
	
}
