package expression;

import expression.binop.*;
import expression.unop.*;
import expression.constant.*;

public interface ExpressionVisitor {

	void visit(BinaryExpression binaryExpression);
	void visit();
	void visit(UnaryExpression unaryExpression);
	void visit(CastExpression castExpression);
	void visit(SizeofType sizeofType);
	void visit(SizeofExpression sizeofExpression);
	void visit(MemberAccessExpression memberAccessExpression);
	void visit(MemberDereferenceExpression memberDereferenceExpression);
	void visit(IndexingExpression indexingExpression);
	void visit(IDExpression idExpression);
	void visit(IntConstantExpression intConstantExpression);
	void visit(FloatConstantExpression floatConstantExpression);
	void visit(StringConstantExpression stringConstantExpression);
	void visit(CharConstantExpression charConstantExpression);
	void visit(FunctionCallExpression functionCallExpression);
}
