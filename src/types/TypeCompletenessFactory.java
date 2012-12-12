package types;

import symbols.SymbolTable;
import transformers.BlockModifier;
import transformers.BlockModifierFactory;

public class TypeCompletenessFactory implements BlockModifierFactory {

	private SymbolTable<StructType> structs;
	private SymbolTable<EnumType> enums;
	
	@Override
	public BlockModifier createModifier() {
		structs = new SymbolTable<StructType>(structs);
		enums = new SymbolTable<EnumType>(enums);
		return new TypeCompletenessChecker(structs, enums);
	}

	@Override
	public void popModifierStack() {
		structs = structs.getParent();
		enums = enums.getParent();
	}

}
