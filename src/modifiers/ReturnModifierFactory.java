package modifiers;

import toplevel.FunctionDefinition;
import transformers.BlockModifier;
import transformers.BlockModifierFactory;
import transformers.EmptyBlockModifier;
import transformers.StatementBlockModifier;
import transformers.StatementModifier;
import transformers.StatementModifierFactory;
import typeresolve.ExpressionTypeMapping;

public class ReturnModifierFactory implements BlockModifierFactory {

	int depth;
	FunctionDefinition func;
	ExpressionTypeMapping map;
	
	public ReturnModifierFactory(ExpressionTypeMapping map) {
		this.map=map;
	}
	
	@Override
	public BlockModifier createModifier(FunctionDefinition def) {
		if(def==null && func==null) {
			return new EmptyBlockModifier();
		}
		if(depth++==0) {
			func=def;
		}
		return new StatementBlockModifier(new StatementModifierFactory() {
			
			@Override
			public StatementModifier create() {
				return new ReturnModifier(func, map);
			}
		});
	}

	@Override
	public void popModifierStack() {
		if(func==null)
			return;
		if(--depth==0)
			func=null;
	}

}
