package transformers;

public class TransformerUtil {
	public static BlockTransformer blockTranForExpressionModifier(final ExpressionModifierFactory emf) {
		return blockTranForStatementModifier(new StatementModifierFactory() {
			
			@Override
			public StatementModifier create() {
				return new ExpressionStatementModifier(emf);
			}
		});
	}
	
	public static BlockTransformer blockTranForStatementModifier(final StatementModifierFactory smf) {
		return new BlockTransformer(new BlockModifierFactory() {
			
			@Override
			public void popModifierStack() {
			}
			
			@Override
			public BlockModifier createModifier() {
				return new StatementBlockModifier(smf);
			}
		});
	}
	
	public static BlockTransformer blockTranForTypeModifier(final TypeModifierFactory tmf) {
		return new BlockTransformer(new BlockModifierFactory() {
			
			@Override
			public void popModifierStack() {
			}
			
			@Override
			public BlockModifier createModifier() {
				return new TypeBlockModifier(tmf);
			}
		});
	}
	
}
