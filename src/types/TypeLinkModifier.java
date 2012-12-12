package types;

import exceptions.SemanticException;
import symbols.SymbolTable;
import transformers.EmptyTypeModifier;

public class TypeLinkModifier extends EmptyTypeModifier {
	
	private SymbolTable<StructType> structs;
	private SymbolTable<EnumType> enums;
	
	public TypeLinkModifier(SymbolTable<StructType> structs, SymbolTable<EnumType> enums) {
		this.structs=structs;
		this.enums=enums;
	}
	
	@Override
	public void visit(final StructType t) {
		StructType st = structs.get(t.tag);
		if(st==t){
			result=st;
			return;
		}
		if(st==null) {
			structs.store(t.tag, t);
			st = t;
		}
		else {
			if(st.members!=null) {
				throw new SemanticException("Redeclaration of struct");
			}
			st.members=t.members;
		}
		result = st;
	}
	
	@Override
	public void visit(EnumType t) {
		EnumType et = enums.get(t.tag);
		if(t==et){
			result = t;
			return;
		}
		if(et==null) {
			enums.store(t.tag, t);
			et=t;
		}
		else {
			if(et.enumerators!=null)
				throw new SemanticException("Redeclaration of enum");
			et.enumerators=t.enumerators;
		}
		result = et;
	}
}
