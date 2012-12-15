package typeresolve;

import declaration.ResolvedDeclaration;
import symbols.SymbolTable;
import toplevel.FunctionDefinition;
import transformers.ExpressionModifier;
import transformers.ExpressionModifierFactory;
import transformers.ExpressionStatementModifier;
import transformers.StatementBlockModifier;
import transformers.StatementModifier;
import transformers.StatementModifierFactory;
import types.Type;

public class TypeResolver extends StatementBlockModifier {
	
	public SymbolTable<Type> types;
	public SymbolTable<FunctionDefinition> funcs;
	
	public TypeResolver(final SymbolTable<Type> symb, final SymbolTable<FunctionDefinition> funcs, final ExpressionTypeMapping resultMapping) {
		super(new StatementModifierFactory() {
			
			@Override
			public StatementModifier create() {
				return new ExpressionStatementModifier(new ExpressionModifierFactory() {
					
					@Override
					public ExpressionModifier create() {
						return new TypeResolverExpressionModifier(symb, funcs, resultMapping);
					}
				});
			}
		});
		types=symb;
		this.funcs=funcs;
	}
	
	@Override
	public void visit(ResolvedDeclaration i) {
		types.store(i.identifier, i.type);
		super.visit(i);
	}
	
	@Override
	public void visit(FunctionDefinition i) {
		super.visit(i);
		funcs.store(i.name, i);
	}
	
}


