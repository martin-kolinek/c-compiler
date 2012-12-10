package types;

import symbols.SymbolTable;
import transformers.BlockModifier;
import transformers.BlockModifierFactory;

public class TypedefRemoverFactory implements BlockModifierFactory {

	SymbolTable<Type> symbs;
	
	public TypedefRemoverFactory(){
		symbs = null;
	}
	
	@Override
	public BlockModifier createModifier() {
		symbs = new SymbolTable<Type>(symbs);
		return new TypedefRemover(symbs);
	}

	@Override
	public void popModifierStack() {
		symbs = symbs.getParent();
	}

}
