package transformers;

import expression.CastExpression;
import expression.Expression;
import expression.ExpressionVisitor;
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

public class ExpressionTransformer implements ExpressionVisitor {

	public ExpressionTransformer(ExpressionModifierFactory mod) {
		modFac=mod;
	}
	
	private ExpressionModifierFactory modFac;
	
	@Override
	public void visit(BinaryExpression binaryExpression) {
		binaryExpression.left=descend(binaryExpression.left);
		binaryExpression.right=descend(binaryExpression.right);
	}

	private Expression descend(Expression e) {
		e.accept(this);
		ExpressionModifier mod = modFac.create();
		e.accept(mod);
		return mod.getResult();
	}
	
	@Override
	public void visit(UnaryExpression unaryExpression) {
		unaryExpression.exp=descend(unaryExpression.exp);
	}

	@Override
	public void visit(CastExpression castExpression) {
		castExpression.exp=descend(castExpression.exp);
	}

	@Override
	public void visit(SizeofType sizeofType) {
	}

	@Override
	public void visit(SizeofExpression sizeofExpression) {
		sizeofExpression.exp=descend(sizeofExpression.exp);
	}

	@Override
	public void visit(MemberAccessExpression memberAccessExpression) {
		memberAccessExpression.exp=descend(memberAccessExpression.exp);
	}

	@Override
	public void visit(MemberDereferenceExpression memberDereferenceExpression) {
		memberDereferenceExpression.exp=descend(memberDereferenceExpression.exp);
	}

	@Override
	public void visit(IndexingExpression indexingExpression) {
		indexingExpression.index=descend(indexingExpression.index);
		indexingExpression.target=descend(indexingExpression.target);
	}

	@Override
	public void visit(IDExpression idExpression) {
	}

	@Override
	public void visit(IntConstantExpression intConstantExpression) {
	}

	@Override
	public void visit(FloatConstantExpression floatConstantExpression) {
	}

	@Override
	public void visit(StringConstantExpression stringConstantExpression) {
	}

	@Override
	public void visit(CharConstantExpression charConstantExpression) {
	}

	@Override
	public void visit(FunctionCallExpression functionCallExpression) {
		for(int i=0; i<functionCallExpression.args.size(); ++i) {
			functionCallExpression.args.set(i, descend(functionCallExpression.args.get(i)));
		}
	}

}
