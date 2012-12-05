package expression;

import expression.binop.*;
import expression.unop.UnaryExpression;

public interface ExpressionVisitor {

	void visit(BinaryExpression binaryExpression);
	void visit();
	void visit(UnaryExpression unaryExpression);
}
