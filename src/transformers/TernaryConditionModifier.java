package transformers;

import expression.TernaryExpression;
import expression.binop.BinaryExpression;
import expression.binop.BinaryOperator;
import expression.constant.IntConstantExpression;

public class TernaryConditionModifier extends EmptyExpressionModifier {

	public void visit(TernaryExpression te){
		result = new TernaryExpression(new BinaryExpression(te.condition, BinaryOperator.NEQ, new IntConstantExpression(0)), te.ontrue, te.onfalse);
	}
	
}
