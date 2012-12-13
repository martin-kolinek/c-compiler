package types;

import symbols.SymbolTable;
import transformers.BlockModifier;
import transformers.BlockModifierFactory;

public class TypeCompletenessFactory implements BlockModifierFactory {

	private SymbolTable<StructType> structs;
	
	@Override
	public BlockModifier createModifier() {
		structs = new SymbolTable<StructType>(structs);
		return new TypeCompletenessChecker(structs);
	}

	@Override
	public void popModifierStack() {
		structs = structs.getParent();
	}

}
