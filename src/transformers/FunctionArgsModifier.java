package transformers;

import expression.Expression;
import expression.FunctionCallExpression;
import expression.binop.BinaryExpression;
import expression.binop.BinaryOperator;

public class FunctionArgsModifier extends EmptyExpressionModifier {

	public void visit(FunctionCallExpression fce){
		result = fce;
		boolean complete = false;
		do {
			Expression last_arg = ((FunctionCallExpression)result).args.get(((FunctionCallExpression)result).args.size() - 1);
			if (last_arg instanceof BinaryExpression){
				if (((BinaryExpression)last_arg).operator == BinaryOperator.COMMA){
					complete = false;
					((FunctionCallExpression)result).args.remove(last_arg);
					((FunctionCallExpression)result).args.add(((BinaryExpression)last_arg).left);
					((FunctionCallExpression)result).args.add(((BinaryExpression)last_arg).right);
				} else {
					complete = true;
				}
			} else {
				complete = true;
			}
		} while(!complete);
	}
}
