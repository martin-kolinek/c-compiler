package types;

import exceptions.SemanticException;
import symbols.SymbolTable;
import transformers.EmptyTypeModifier;

public class TypeLinkModifier extends EmptyTypeModifier {
	
	private SymbolTable<StructType> structs;
	
	public TypeLinkModifier(SymbolTable<StructType> structs) {
		this.structs=structs;
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
			if(st.members!=null && t.members!=null) {
				throw new SemanticException("Redeclaration of struct");
			}
			if(st.members==null)
				st.members=t.members;
		}
		result = st;
	}
}
