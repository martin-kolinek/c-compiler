package transformers;

import declaration.ResolvedDeclaration;
import declaration.TypedefDeclaration;
import statements.Statement;
import toplevel.FunctionDefinition;
import toplevel.FunctionParameter;

public class ExpressionBlockModifier extends EmptyBlockModifier {
	private TypeModifierFactory tf;
	private StatementModifierFactory sf;
	private ExpressionModifierFactory ef;
	public ExpressionBlockModifier(final ExpressionModifierFactory fac) {
		ef=fac;
		tf = new TypeModifierFactory() {
			
			@Override
			public TypeModifier create() {
				return new ExpressionTypeModifier(fac);
			}
		};
		sf = new StatementModifierFactory() {
			
			@Override
			public StatementModifier create() {
				return new ExpressionStatementModifier(fac);
			}
		};
	}
	
	@Override
	public void visit(FunctionDefinition i) {
		
		i.returnType = TransformerUtil.transformType(i.returnType, tf);
		for(FunctionParameter p:i.parameters) {
			p.type=TransformerUtil.transformType(p.type, tf);
		}
		if(i.body!=null)
			i.body=TransformerUtil.transformStatement(i.body, sf);
		super.visit(i);
	}
	
	@Override
	public void visit(ResolvedDeclaration i) {
		i.type=TransformerUtil.transformType(i.type, tf);
		if(i.initializer!=null)
			i.initializer=TransformerUtil.transformInitializer(i.initializer, ef);
		super.visit(i);
	}
	
	@Override
	public void visit(Statement i) {
		super.visit(TransformerUtil.transformStatement(i, sf));
	}
	
	@Override
	public void visit(TypedefDeclaration i) {
		i.type=TransformerUtil.transformType(i.type, tf);
		super.visit(i);
	}
	
}
