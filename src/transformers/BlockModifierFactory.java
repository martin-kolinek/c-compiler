package transformers;

//Toto sa pouziva na riesenie scopov pri kazdom scope sa zavola createModifier a na konci sa zavola popModifierStack
public interface BlockModifierFactory {
	BlockModifier createModifier();
	void popModifierStack();
}
