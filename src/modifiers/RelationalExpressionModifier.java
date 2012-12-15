package modifiers;

import expression.binop.BinaryExpression;
import expression.binop.BinaryOperator;
import expression.unop.UnaryExpression;
import expression.unop.UnaryOperator;
import transformers.EmptyExpressionModifier;

public class RelationalExpressionModifier extends EmptyExpressionModifier {
	@Override
	public void visit(BinaryExpression e) {
		switch(e.operator) {
		case LT:
			result = new BinaryExpression(e.right, BinaryOperator.GT, e.left);
			break;
		case LET:
			result = new UnaryExpression(new BinaryExpression(e.left, BinaryOperator.GT, e.right), UnaryOperator.NOT);
			break;
		case GET: 
			result = new UnaryExpression(new BinaryExpression(e.right, BinaryOperator.GT, e.left), UnaryOperator.NOT);
			break;
		case NEQ:
			result = new UnaryExpression(new BinaryExpression(e.left, BinaryOperator.EQ, e.right), UnaryOperator.NOT);
			break;
		default:
			super.visit(e);
		}
	}
}
