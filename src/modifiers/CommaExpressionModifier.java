package modifiers;

import transformers.EmptyExpressionModifier;
import expression.CommaExpression;

public class CommaExpressionModifier extends EmptyExpressionModifier {
	
	public void visit(CommaExpression ce){
		if (ce.expressions.size() == 1){
			result = ce.expressions.get(0);
		} else {
			result = ce;
		}
	}

}
