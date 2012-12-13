package types;

import expression.Expression;
import symbols.SymbolTable;
import transformers.BlockModifier;
import transformers.BlockModifierFactory;
import transformers.CompoundBlockModifier;
import transformers.ExpressionBlockModifier;
import transformers.ExpressionModifier;
import transformers.ExpressionModifierFactory;
import transformers.TypeBlockModifier;
import transformers.TypeModifier;
import transformers.TypeModifierFactory;

public class EnumRemoverFactory implements BlockModifierFactory {

	private SymbolTable<Expression> enumerators;
	
	@Override
	public BlockModifier createModifier() {
		enumerators = new SymbolTable<Expression>(enumerators);
		return new CompoundBlockModifier(
				new TypeBlockModifier(new TypeModifierFactory() {
					
					@Override
					public TypeModifier create() {
						return new EnumRemoverTypeMod(enumerators);
					}
				}), 
				new ExpressionBlockModifier(new ExpressionModifierFactory() {
					
					@Override
					public ExpressionModifier create() {
						return new EnumRemoverExpressionMod(enumerators);
					}
				}));
	}

	@Override
	public void popModifierStack() {
		enumerators = enumerators.getParent();
	}

}
