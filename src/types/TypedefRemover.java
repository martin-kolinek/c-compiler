package types;

import declaration.ResolvedDeclaration;
import declaration.TypedefDeclaration;
import exceptions.SemanticException;
import symbols.SymbolTable;
import transformers.EmptyTypeModifier;
import transformers.TypeBlockModifier;
import transformers.TypeModifier;
import transformers.TypeModifierFactory;

public class TypedefRemover extends TypeBlockModifier {

	SymbolTable<Type> types;
	public TypedefRemover(final SymbolTable<Type> types){
		super(new TypeModifierFactory() {
			
			@Override
			public TypeModifier create() {
				return new TypedefTypeChecker(types);
			}
		});
		this.types=types;
	}
	
	@Override
	public void visit(TypedefDeclaration i) {
		types.store(i.id, i.type);
		ResolvedDeclaration res = new ResolvedDeclaration();
		res.type=i.type;
		res.identifier = null;
		res.initializer=null;
		res.accept(this);
	}
}

class TypedefTypeChecker extends EmptyTypeModifier{

	private SymbolTable<Type> symbTable;
	
	public TypedefTypeChecker(SymbolTable<Type> symbTable) {
		this.symbTable = symbTable;
	}
	
	@Override
	public void visit(TypedefType t) {
		result = symbTable.get(t.identifier);
		if(result==null) {
			throw new SemanticException("Unknown typedef-name");
		}
	}
}
