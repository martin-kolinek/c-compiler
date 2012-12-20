package codegen;

import declaration.ResolvedDeclaration;
import types.ArrayType;
import types.EnumType;
import types.PointerType;
import types.PrimitiveType;
import types.StructType;
import types.TypeVisitor;
import types.TypedefType;

public class CodeGenSizeOfVisitor implements TypeVisitor {

	private BlockCodeGenerator cg;
	private String result;
	
	public CodeGenSizeOfVisitor(BlockCodeGenerator cg) {
		this.cg = cg;
	}
		
	@Override
	public void visit(StructType t) {
		result = "0";
		for(ResolvedDeclaration mem : t.members) {
			String n = cg.getNextregister();
			cg.str.writeAssignment(n, "add", "i32", cg.getSizeOfResult(mem.type), ",", result);
			result = n;
		}
	}

	@Override
	public void visit(ArrayType t) {
		result = cg.getNextregister();
		cg.str.writeAssignment(result, "mul", "i32", cg.getSizeOfResult(t.elementType), ",", cg.getExpressionRegister(t.size));
	}

	@Override
	public void visit(TypedefType t) {
		assert false;
	}

	@Override
	public void visit(PrimitiveType t) {
		result = Integer.toString(t.size==0?1:t.size);
	}

	@Override
	public void visit(EnumType t) {
		assert false;
	}

	@Override
	public void visit(PointerType t) {
		result = "8";
	}

	public String getResult() {
		return result;
	}

}
