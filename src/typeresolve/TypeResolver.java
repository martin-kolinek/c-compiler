package typeresolve;

import declaration.ResolvedDeclaration;
import symbols.SymbolTable;
import toplevel.FunctionDefinition;
import toplevel.FunctionParameter;
import transformers.ExpressionModifier;
import transformers.ExpressionModifierFactory;
import transformers.ExpressionStatementModifier;
import transformers.StatementBlockModifier;
import transformers.StatementModifier;
import transformers.StatementModifierFactory;
import types.Type;

public class TypeResolver extends StatementBlockModifier {
	
	private SymbolTable<Type> types;
	
	public TypeResolver(final SymbolTable<Type> symb, final ExpressionTypeMapping resultMapping) {
		super(new StatementModifierFactory() {
			
			@Override
			public StatementModifier create() {
				return new ExpressionStatementModifier(new ExpressionModifierFactory() {
					
					@Override
					public ExpressionModifier create() {
						return new TypeResolverExpressionModifier(symb, resultMapping);
					}
				});
			}
		});
		types=symb;
	}
	
	@Override
	public void visit(ResolvedDeclaration i) {
		types.store(i.identifier, i.type);
		super.visit(i);
	}
	
	@Override
	public void visit(FunctionDefinition i) {
		types=new SymbolTable<Type>(types);
		for(FunctionParameter fp : i.parameters) {
			types.store(fp.id, fp.type);
		}
		super.visit(i);
		types=types.getParent();
	}
	
}


