package transformers;

import statements.Statement;
import toplevel.FunctionDefinition;

public class StatementBlockModifier extends EmptyBlockModifier {
	
	private StatementModifierFactory smf;
	
	public StatementBlockModifier(StatementModifierFactory smf) {
		this.smf=smf;
	}
	
	public Statement transform(Statement s) {
		StatementTransformer trans = new StatementTransformer(smf);
		s.accept(trans);
		StatementModifier m = smf.create();
		s.accept(m);
		return m.getResult();
	}
	
	@Override
	public void visit(Statement i) {
		result.add(transform(i));
	}
	
	@Override
	public void visit(FunctionDefinition i) {
		if(i.body!=null)
			i.body=transform(i.body);
		super.visit(i);
	}
}
