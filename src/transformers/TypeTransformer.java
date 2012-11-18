package transformers;

import declaration.ResolvedDeclaration;
import types.ArrayType;
import types.EnumType;
import types.PointerType;
import types.PrimitiveType;
import types.StructType;
import types.Type;
import types.TypeVisitor;
import types.TypedefType;

public class TypeTransformer implements TypeVisitor {

	TypeModifierFactory modFac;
	
	public TypeTransformer(TypeModifierFactory mod) {
		modFac=mod;
	}
	
	private Type descend(Type t) {
		TypeModifier mod = modFac.create();
		t.accept(mod);
		return mod.getResult();
	}
	
	@Override
	public void visit(StructType t) {
		for(int i=0; i<t.members.size(); i++){
			ResolvedDeclaration d = t.members.get(i);
			d.type=descend(d.type);
		}
	}

	@Override
	public void visit(ArrayType t) {
		t.elementType=descend(t.elementType);
	}

	@Override
	public void visit(TypedefType t) {
	}

	@Override
	public void visit(PrimitiveType t) {
	}

	@Override
	public void visit(EnumType t) {
	}

	@Override
	public void visit(PointerType t) {
		t.pointedToType=descend(t.pointedToType);
	}

}
