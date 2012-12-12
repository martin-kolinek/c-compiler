package types;

import exceptions.SemanticException;
import symbols.SymbolTable;
import transformers.EmptyTypeModifier;

public class TypeLinkModifier extends EmptyTypeModifier {
	
	private SymbolTable<StructType> structs;
	private SymbolTable<EnumType> enums;
	private Type parentType;
	
	public TypeLinkModifier(SymbolTable<StructType> structs, SymbolTable<EnumType> enums, Type par) {
		this.structs=structs;
		this.enums=enums;
		parentType=par;
	}
	
	@Override
	public void visit(final StructType t) {
		if(t==parentType) {
			throw new SemanticException("Struct containg itself");
		}
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
			if(st.members==null) {
				st.members=t.members;
			}
			else if(t.members.size() != st.members.size()) {
				throw new SemanticException("Incompatible redefinition of struct");
			}
			else {
				for(int i=0; i<t.members.size(); i++){
					if(st.members.get(i).type!=t.members.get(i).type ||
							!st.members.get(i).identifier.equals(t.members.get(i).identifier))
						throw new SemanticException("Incompatible redefinition of struct");
				}
			}
		}
		result = st;
	}
}
