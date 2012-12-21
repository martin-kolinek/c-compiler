package codegen;

import types.Type;

import declaration.ResolvedDeclaration;
import typeresolve.AutomaticConversions;
import types.ArrayType;
import types.EnumType;
import types.PointerType;
import types.PrimitiveType;
import types.StructType;
import types.TypeVisitor;
import types.TypedefType;

public class CodeGenTypeVisitor implements TypeVisitor {

	BlockCodeGenerator cg;
	boolean arr;
	private String Typ;

	public CodeGenTypeVisitor(BlockCodeGenerator cg, boolean properArrays) {
		this.cg=cg;
		arr = properArrays;
	}

	@Override
	public void visit(StructType t) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		boolean f = true;
		for(ResolvedDeclaration r:t.members) {
			if(!f) {
				sb.append(",");
			}
			f=false;
			sb.append(cg.getProperTypeString(r.type));
		}
		sb.append("}");
		Typ=sb.toString();
	}

	@Override
	public void visit(ArrayType t) {
		if(!arr) {
			visit(AutomaticConversions.arrayToPtr(t));
		}
		else {
			String subt = cg.getProperTypeString(t.elementType);
			String size = cg.getExpressionRegister(t.size);
			Typ="["+size+" x " +subt + "]";
		}
	}

	@Override
	public void visit(TypedefType t) {
		assert false; //uz nie su
	}

	@Override
	public void visit(PrimitiveType t) {
		if(t==PrimitiveType.VOID) {
			Typ="void";
			return;
		}
		if (!t.floating){
			Typ = "i" + Integer.toString(t.size*8);
		}
		else {
			if(t==PrimitiveType.DOUBLE)
				Typ = "double";
			else
				Typ = "float";
		}
	}

	@Override
	public void visit(EnumType t) {
		assert false; //uz nie su
	}

	@Override
	public void visit(PointerType t) {
		Type t2 = t.pointedToType;
		if(t2==PrimitiveType.VOID)
			t2=PrimitiveType.UCHAR;
		Typ = cg.getTypeString(t2)+"*";
	}

	public String GetTypeText() {
		return this.Typ;
	}
}
