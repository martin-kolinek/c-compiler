package expression;

import expression.binop.*;

public interface ExpressionVisitor {

	void visit(AdditionExpresion additionExpression);
}
