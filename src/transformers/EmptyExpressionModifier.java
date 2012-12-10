package transformers;

import expression.CastExpression;
import expression.Expression;
import expression.FunctionCallExpression;
import expression.IDExpression;
import expression.IndexingExpression;
import expression.MemberAccessExpression;
import expression.MemberDereferenceExpression;
import expression.SizeofExpression;
import expression.SizeofType;
import expression.binop.BinaryExpression;
import expression.constant.CharConstantExpression;
import expression.constant.FloatConstantExpression;
import expression.constant.IntConstantExpression;
import expression.constant.StringConstantExpression;
import expression.unop.UnaryExpression;

public class EmptyExpressionModifier implements ExpressionModifier {

	protected Expression result;
	
	@Override
	public void visit(BinaryExpression e) {
		result = e;
	}

	@Override
	public void visit(UnaryExpression e) {
		result = e;
	}

	@Override
	public void visit(CastExpression e) {
		result = e;
	}

	@Override
	public void visit(SizeofType e) {
		result = e;
	}

	@Override
	public void visit(SizeofExpression e) {
		result = e;
	}

	@Override
	public void visit(MemberAccessExpression e) {
		result = e;
	}

	@Override
	public void visit(MemberDereferenceExpression e) {
		result = e;
	}

	@Override
	public void visit(IndexingExpression e) {
		result = e;
	}

	@Override
	public void visit(IDExpression e) {
		result = e;
	}

	@Override
	public void visit(IntConstantExpression e) {
		result = e;
	}

	@Override
	public void visit(FloatConstantExpression e) {
		result = e;
	}

	@Override
	public void visit(StringConstantExpression e) {
		result = e;
	}

	@Override
	public void visit(CharConstantExpression e) {
		result = e;
	}

	@Override
	public void visit(FunctionCallExpression e) {
		result = e;
	}

	@Override
	public Expression getResult() {
		return result;
	}

}
