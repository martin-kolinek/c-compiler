package transformers;

import statements.Case;
import statements.DowhileStatement;
import statements.ForStatement;
import statements.IfStatement;
import statements.OneexpressionStatement;
import statements.ReturnStatement;
import statements.SwitchStatement;
import statements.WhileStatement;

public class ExpressionStatementModifier extends EmptyStatementModifier {
	private ExpressionModifierFactory emf;
	public ExpressionStatementModifier(ExpressionModifierFactory emf) {
		this.emf=emf;
	}
	
	@Override
	public void visit(OneexpressionStatement s) {
		s.expr=TransformerUtil.transformExpression(s.expr, emf);
		super.visit(s);
	}
	
	@Override
	public void visit(IfStatement s) {
		s.cond=TransformerUtil.transformExpression(s.cond, emf);
		super.visit(s);
	}
	
	@Override
	public void visit(ReturnStatement s) {
		if(s.exp!=null)
			s.exp=TransformerUtil.transformExpression(s.exp, emf);
		super.visit(s);
	}
	
	@Override
	public void visit(DowhileStatement s) {
		s.condition=TransformerUtil.transformExpression(s.condition, emf);
		super.visit(s);
	}
	
	@Override
	public void visit(ForStatement s) {
		assert false; //povedzme ze forov sme sa uz zbavili
		super.visit(s);
	}
	
	@Override
	public void visit(SwitchStatement s) {
		s.expr = TransformerUtil.transformExpression(s.expr, emf);
		for(Case c : s.cases) {
			if(c.cond!=null)
				c.cond = TransformerUtil.transformExpression(c.cond, emf);
		}
		super.visit(s);
	}
	@Override
	public void visit(WhileStatement s) {
		s.condition=TransformerUtil.transformExpression(s.condition, emf);
		super.visit(s);
	}
}
