package types;

import declaration.ResolvedDeclaration;
import exceptions.SemanticException;
import symbols.SymbolTable;
import transformers.TypeBlockModifier;
import transformers.TypeModifier;
import transformers.TypeModifierFactory;

public class TypeCompletenessChecker extends TypeBlockModifier {
	
	public TypeCompletenessChecker(final SymbolTable<StructType> structs) {
		super(new TypeModifierFactory() {
			
			@Override
			public TypeModifier create() {
				return new TypeLinkModifier(structs);
			}
		});
	}
	
	@Override
	public void visit(ResolvedDeclaration i) {
		super.visit(i);
		if(!TypeClass.isComplete(i.type))
			throw new SemanticException("Declaration of variable with incomplete type", i);
	}

}
