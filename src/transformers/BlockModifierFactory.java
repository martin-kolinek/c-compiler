package transformers;

import toplevel.FunctionDefinition;

//Toto sa pouziva na riesenie scopov pri kazdom scope sa zavola createModifier a na konci sa zavola popModifierStack
public interface BlockModifierFactory {
	//parameter def obsahuje obsahuje functiondefinition, ktorej telo sa bude tymto blockmodifierfactory spracovavat
	BlockModifier createModifier(FunctionDefinition def);
	void popModifierStack();
}
