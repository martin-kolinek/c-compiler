package transformers;

import types.ArrayType;
import types.EnumType;
import types.PointerType;
import types.PrimitiveType;
import types.StructType;
import types.Type;
import types.TypedefType;

public class EmptyTypeModifier implements TypeModifier {

	protected Type result;
	
	@Override
	public void visit(StructType t) {
		result = t;
	}

	@Override
	public void visit(ArrayType t) {
		result = t;
	}

	@Override
	public void visit(TypedefType t) {
		result = t;
	}

	@Override
	public void visit(PrimitiveType t) {
		result = t;
	}

	@Override
	public void visit(EnumType t) {
		result = t;
	}

	@Override
	public void visit(PointerType t) {
		result = t;
	}

	@Override
	public Type getResult() {
		return result;
	}

}
