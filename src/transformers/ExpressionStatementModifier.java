package transformers;

import statements.OneexpressionStatement;

public class ExpressionStatementModifier extends EmptyStatementModifier {
	private ExpressionModifierFactory emf;
	public ExpressionStatementModifier(ExpressionModifierFactory emf) {
		this.emf=emf;
	}
	
	@Override
	public void visit(OneexpressionStatement s) {
		ExpressionTransformer trans = new ExpressionTransformer(emf);
		s.exp.accept(trans);
		ExpressionModifier m = emf.create();
		s.exp.accept(m);
		s.exp=m.getResult();
		super.visit(s);
	}
	
}
