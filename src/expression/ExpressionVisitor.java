package expression;

import expression.binop.*;

public interface ExpressionVisitor {

	void visit(BinaryExpression binaryExpression);
	void visit();
}
