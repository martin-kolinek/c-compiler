package types;

import declaration.ResolvedDeclaration;
import exceptions.SemanticException;
import symbols.SymbolTable;
import transformers.TypeBlockModifier;
import transformers.TypeModifier;
import transformers.TypeModifierFactory;

public class TypeCompletenessChecker extends TypeBlockModifier {
	
	public TypeCompletenessChecker(final SymbolTable<StructType> structs, final SymbolTable<EnumType> enums) {
		super(new TypeModifierFactory() {
			
			@Override
			public TypeModifier create() {
				return new TypeLinkModifier(structs, enums);
			}
		});
	}
	
	@Override
	public void visit(ResolvedDeclaration i) {
		if(!TypeClass.isComplete(i.type))
			throw new SemanticException("Declaration of variable with incomplete type");
		super.visit(i);
	}

}
