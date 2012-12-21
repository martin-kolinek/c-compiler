package transformers;

import declaration.ResolvedDeclaration;
import expression.CastExpression;
import expression.SizeofType;
import statements.Statement;
import toplevel.FunctionDefinition;
import toplevel.FunctionParameter;

public class TypeBlockModifier extends EmptyBlockModifier {
	
	private TypeModifierFactory tmf;
	private StatementModifierFactory smf;
	private ExpressionModifierFactory emf;
	public TypeBlockModifier(final TypeModifierFactory tmf) {
		
		this.tmf=tmf;
		emf = new ExpressionModifierFactory() {
			
			@Override
			public ExpressionModifier create() {
				return new TypeExpressionModifier(tmf);
			}
		};
		smf = new StatementModifierFactory() {
			
			@Override
			public StatementModifier create() {
				return new ExpressionStatementModifier(emf);
			}
		};
	}
	
	@Override
	public void visit(FunctionDefinition i) {
		i.returnType=TransformerUtil.transformType(i.returnType, tmf);
		for(FunctionParameter fp : i.parameters) {
			fp.type=TransformerUtil.transformType(fp.type, tmf);;
		}
		if(i.body!=null)
			i.body=TransformerUtil.transformStatement(i.body, smf);
		super.visit(i);
	}
	
	@Override
	public void visit(ResolvedDeclaration i) {
		i.type=TransformerUtil.transformType(i.type, tmf);
		if(i.initializer!=null)
			TransformerUtil.transformInitializer(i.initializer, emf);
		super.visit(i);
	}
	
	@Override
	public void visit(Statement i) {
		super.visit(TransformerUtil.transformStatement(i, smf));
	}

}

class TypeExpressionModifier extends EmptyExpressionModifier {
	TypeModifierFactory mod;
	public TypeExpressionModifier(TypeModifierFactory fac) {
		mod=fac;
	}
	
	@Override
	public void visit(CastExpression e) {
		e.type = TransformerUtil.transformType(e.type, mod);
		super.visit(e);
	}
	
	@Override
	public void visit(SizeofType e) {
		e.type = TransformerUtil.transformType(e.type, mod);
		super.visit(e);
	}
}
