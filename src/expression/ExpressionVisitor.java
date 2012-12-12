package expression;

import expression.binop.*;
import expression.unop.*;
import expression.constant.*;

public interface ExpressionVisitor {

	void visit(BinaryExpression e);
	void visit(UnaryExpression e);
	void visit(CastExpression e);
	void visit(SizeofType e);
	void visit(SizeofExpression e);
	void visit(MemberAccessExpression e);
	void visit(MemberDereferenceExpression e);
	void visit(IndexingExpression e);
	void visit(IDExpression e);
	void visit(IntConstantExpression e);
	void visit(FloatConstantExpression e);
	void visit(StringConstantExpression e);
	void visit(CharConstantExpression e);
	void visit(FunctionCallExpression e);
	void visit(TernaryExpression e);
	void visit(CommaExpression e);
	void visit(AssignmentExpression e);
}
