package transformers;

public class CompoundBlockModifierFactory implements BlockModifierFactory {
	BlockModifierFactory f1;
	BlockModifierFactory f2;
	public CompoundBlockModifierFactory(BlockModifierFactory f1, BlockModifierFactory f2) {
		this.f1=f1;
		this.f2=f2;
	}
	
	@Override
	public BlockModifier createModifier() {
		return new CompoundBlockModifier(f1.createModifier(), f2.createModifier());
	}

	@Override
	public void popModifierStack() {
		f1.popModifierStack();
		f2.popModifierStack();
	}

}
