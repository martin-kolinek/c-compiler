package transformers;

import expression.binop.BinaryExpression;
import expression.binop.BinaryOperator;
import expression.constant.IntConstantExpression;
import expression.unop.UnaryExpression;
import expression.unop.UnaryOperator;

public class UnaryChargeModifier extends EmptyExpressionModifier {
	
	public void visit(UnaryExpression ue){
		if (ue.op == UnaryOperator.PLUS){
			result = ue.exp;
		}
		else if (ue.op == UnaryOperator.MINUS){
			result = new BinaryExpression(new IntConstantExpression(0), BinaryOperator.MINUS, ue.exp);
		}
		else
			super.visit(ue);
	}

}
