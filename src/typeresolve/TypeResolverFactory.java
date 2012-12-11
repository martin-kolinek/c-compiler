package typeresolve;

import symbols.SymbolTable;
import toplevel.FunctionDefinition;
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
	public BlockModifier createModifier() {
		symbs = new SymbolTable<Type>(symbs);
		funcs = new SymbolTable<FunctionDefinition>(funcs);
		return new TypeResolver(symbs, funcs, mapping);
	}

	@Override
	public void popModifierStack() {
		funcs = funcs.getParent();
		symbs = symbs.getParent();
	}

}
