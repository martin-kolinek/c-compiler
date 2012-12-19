package modifiers;

import java.util.HashSet;

import exceptions.SemanticException;
import expression.constant.IntConstantExpression;
import statements.Case;
import statements.SwitchStatement;
import transformers.ConstantExpressionModifier;
import transformers.EmptyStatementModifier;
import transformers.ExpressionModifier;
import transformers.ExpressionModifierFactory;
import transformers.TransformerUtil;

public class SwitchModifier extends EmptyStatementModifier {
	@Override
	public void visit(SwitchStatement s) {
		HashSet<Long> values = new HashSet<Long>();
		boolean def = false;
		for(Case c:s.cases) {
			if(c.cond!=null) {
				c.cond = TransformerUtil.transformExpression(c.cond, new ExpressionModifierFactory() {
					@Override
					public ExpressionModifier create() {
						return new ConstantExpressionModifier();
					}
				});
				if(!(c.cond instanceof IntConstantExpression)) 
					throw new SemanticException("Case value is not a constant integer expression");
				long val = ((IntConstantExpression)c.cond).value;
				if(values.contains(val))
					throw new SemanticException("Same constant used more than once in switch cases");
				values.add(val);
			}
			else {
				if(def)
					throw new SemanticException("Multiple defaults in a switch");
				def = true;
			}
		}
		super.visit(s);
	}
}
