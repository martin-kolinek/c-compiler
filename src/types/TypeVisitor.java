package types;

public interface TypeVisitor {

	void visit(IntType intType);

	void visit(CharType charType);

	void visit(ShortType shortType);

	void visit(LongType longType);

	void visit(StructType structType);

	void visit(ArrayType arrayType);

	void visit(TypedefType typedefType);
	
}
