package codegen;

import types.ArrayType;
import types.EnumType;
import types.PointerType;
import types.PrimitiveType;
import types.StructType;
import types.TypeVisitor;
import types.TypedefType;

public class CodeGenTypeVisitor implements TypeVisitor {

	private VisitPack pack;
	private String Typ;

	public CodeGenTypeVisitor(VisitPack pack) {
		// TODO Auto-generated constructor stub
		this.pack=pack;
	}

	@Override
	public void visit(StructType t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ArrayType t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TypedefType t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PrimitiveType t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(EnumType t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PointerType t) {
		// TODO Auto-generated method stub

	}

	public String GetTypeText() {
		// TODO Auto-generated method stub
		return this.Typ;
	}

}
