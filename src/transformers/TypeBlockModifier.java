package transformers;

import declaration.ResolvedDeclaration;
import expression.CastExpression;
import statements.Statement;
import toplevel.FunctionDefinition;
import toplevel.FunctionParameter;

public class TypeBlockModifier extends EmptyBlockModifier {
	
	private TypeModifierFactory tmf;
	public TypeBlockModifier(TypeModifierFactory tmf) {
		this.tmf=tmf;
	}
	
	@Override
	public void visit(FunctionDefinition i) {
		i.returnType=TransformerUtil.transformType(i.returnType, tmf);
		for(FunctionParameter fp : i.parameters) {
			fp.type=TransformerUtil.transformType(fp.type, tmf);;
		}
		super.visit(i);
	}
	
	@Override
	public void visit(ResolvedDeclaration i) {
		i.type=TransformerUtil.transformType(i.type, tmf);
		super.visit(i);
	}
	
	@Override
	public void visit(Statement i) {
		TransformerUtil.transformStatement(i, new StatementModifierFactory() {
			
			@Override
			public StatementModifier create() {
				return new ExpressionStatementModifier(new ExpressionModifierFactory() {
					
					@Override
					public ExpressionModifier create() {
						return new TypeExpressionModifier(tmf);
					}
				});
			}
		});
	}

}

class TypeExpressionModifier extends EmptyExpressionModifier {
	TypeModifierFactory mod;
	public TypeExpressionModifier(TypeModifierFactory fac) {
		mod=fac;
	}
	
	@Override
	public void visit(CastExpression e) {
		TypeModifier tm = mod.create();
		e.type.accept(tm);
		e.type=tm.getResult();
		super.visit(e);
	}
}
