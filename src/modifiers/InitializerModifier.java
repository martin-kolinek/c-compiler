package modifiers;

import declaration.ResolvedDeclaration;
import transformers.EmptyBlockModifier;

public class InitializerModifier extends EmptyBlockModifier {
	@Override
	public void visit(ResolvedDeclaration i) {
		
		i.initializer = null;
		result.add(i);
	}
}
