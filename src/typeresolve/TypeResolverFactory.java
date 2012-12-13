package typeresolve;

import symbols.SymbolTable;
import toplevel.FunctionDefinition;
import toplevel.FunctionParameter;
import transformers.BlockModifier;
import transformers.BlockModifierFactory;
import types.Type;

public class TypeResolverFactory implements BlockModifierFactory {

	private SymbolTable<Type> symbs;
	private SymbolTable<FunctionDefinition> funcs;
	private ExpressionTypeMapping mapping;
	public ExpressionTypeMapping getResultMapping(){
		return mapping;
	}
	
	public TypeResolverFactory() {
		mapping = new ExpressionTypeMapping();
		funcs=null;
		symbs=null;
	}
	
	@Override
	public BlockModifier createModifier(FunctionDefinition def) {
		symbs = new SymbolTable<Type>(symbs);
		funcs = new SymbolTable<FunctionDefinition>(funcs);
		if(def!=null) {
			funcs.store(def.name, def);
			for(FunctionParameter p : def.parameters) {
				symbs.store(p.id, p.type);
			}
		}
		return new TypeResolver(symbs, funcs, mapping);
	}

	@Override
	public void popModifierStack() {
		funcs = funcs.getParent();
		symbs = symbs.getParent();
	}

}
