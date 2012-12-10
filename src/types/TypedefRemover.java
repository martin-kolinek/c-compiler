package types;

import declaration.ResolvedDeclaration;
import declaration.TypedefDeclaration;
import exceptions.SemanticException;
import symbols.SymbolTable;
import toplevel.FunctionDefinition;
import toplevel.FunctionParameter;
import transformers.EmptyBlockModifier;
import transformers.EmptyTypeModifier;
import transformers.TypeModifier;
import transformers.TypeModifierFactory;
import transformers.TypeTransformer;

public class TypedefRemover extends EmptyBlockModifier {

	SymbolTable<Type> types;
	public TypedefRemover(SymbolTable<Type> types){
		this.types=types;
	}
	
	private Type transform(Type t){
		TypeTransformer tt = new TypeTransformer(new TypeModifierFactory() {
			@Override
			public TypeModifier create() {
				return new TypedefTypeChecker(types);
			}
		});
		t.accept(tt);
		TypeModifier mod = new TypedefTypeChecker(types);
		t.accept(mod);
		return mod.getResult();
	}
	
	@Override
	public void visit(FunctionDefinition i) {
		i.returnType=transform(i.returnType);
		for(FunctionParameter fp : i.parameters) {
			fp.type=transform(fp.type);
		}
	}

	@Override
	public void visit(TypedefDeclaration i) {
		types.store(i.id, i.type);
	}

	@Override
	public void visit(ResolvedDeclaration i) {
		i.type=transform(i.type);
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
