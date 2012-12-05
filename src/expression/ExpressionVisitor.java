package expression;

import expression.binop.*;
import expression.unop.UnaryExpression;

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
}
