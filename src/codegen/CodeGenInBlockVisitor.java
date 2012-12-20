package codegen;

import declaration.Declaration;
import declaration.ResolvedDeclaration;
import declaration.TypedefDeclaration;
import statements.Statement;
import toplevel.FunctionDefinition;
import toplevel.InBlockVisitor;
import types.ArrayType;
import types.TypeClass;

public class CodeGenInBlockVisitor implements InBlockVisitor {

	BlockCodeGenerator cg;
	public CodeGenInBlockVisitor(BlockCodeGenerator cg) {
		this.cg=cg;
	}
	
	@Override
	public void visit(Statement i) {
		cg.generateStatement(i);
	}

	@Override
	public void visit(Declaration i) {
		assert false;
	}

	@Override
	public void visit(FunctionDefinition i) {
		assert false; //we don't have support for functions in functions
	}

	@Override
	public void visit(TypedefDeclaration i) {
		assert false;
	}

	public String getArraySize(ArrayType t) {
		String size = cg.getExpressionRegister(t.size);
		String sub = "1";
		if(TypeClass.isArray(t.elementType))
			sub = getArraySize((ArrayType)t.elementType);
		String res = cg.getNextregister();
		cg.str.writeAssignment(res, "mul i32", size, ",", sub);
		return res;
	}
	
	public String getArrayType(ArrayType t) {
		if(TypeClass.isArray(t.elementType))
			return getArrayType((ArrayType)t.elementType) + "*";
		return cg.getTypeString(t.elementType);
	}
	
	@Override
	public void visit(ResolvedDeclaration i) {
		String reg = cg.getNextregister();
		String tp = cg.getTypeString(i.type);
		String size = "";
		if(TypeClass.isArray(i.type)) {
			ArrayType at = (ArrayType)i.type;
			tp = getArrayType(at);
			size = ", i32" + getArraySize(at);
		}
		cg.str.writeAssignment(reg, "alloca", tp, size);
		cg.storeID(i.identifier, reg);
	}

}
