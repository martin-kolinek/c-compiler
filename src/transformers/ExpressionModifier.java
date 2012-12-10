package transformers;

import expression.Expression;
import expression.ExpressionVisitor;

public interface ExpressionModifier extends ExpressionVisitor {
	Expression getResult();
}
