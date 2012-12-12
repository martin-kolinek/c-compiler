package transformers;

import expression.IndexingExpression;
import expression.MemberAccessExpression;
import expression.MemberDereferenceExpression;
import expression.binop.BinaryExpression;
import expression.binop.BinaryOperator;
import expression.unop.UnaryExpression;
import expression.unop.UnaryOperator;

public class PointerModifier extends EmptyExpressionModifier{
	
	/**
	 * a->b to (*a).b
	 */
	public void visit(MemberDereferenceExpression mde){
		result = new MemberAccessExpression(new UnaryExpression(mde.exp, UnaryOperator.PTR), mde.id);
	}
	
	/**
	 * a[b] to *(a+b)
	 */
	public void visit(IndexingExpression ie){
		result = new UnaryExpression(new BinaryExpression(ie.target, BinaryOperator.PLUS, ie.index), UnaryOperator.PTR);
	}

}
