package transformers;

import statements.Statement;
import toplevel.FunctionDefinition;

public class StatementBlockModifier extends EmptyBlockModifier {
	
	private StatementModifierFactory smf;
	
	public StatementBlockModifier(StatementModifierFactory smf) {
		this.smf=smf;
	}
	
	@Override
	public void visit(Statement i) {
		result.add(TransformerUtil.transformStatement(i, smf));
	}
	
	@Override
	public void visit(FunctionDefinition i) {
		if(i.body!=null)
			i.body=TransformerUtil.transformStatement(i.body, smf);
		super.visit(i);
	}
}
