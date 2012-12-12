package modifiers;

import java.util.HashMap;

import transformers.EmptyExpressionModifier;

import expression.AssignmentExpression;
import expression.binop.*;

/**
 * Zjednodusovanie priradeni typu a += b; na b = a + b;
 * @author GREPY
 *
 */
public class AssignmentModifier extends EmptyExpressionModifier {
	
	private HashMap<BinaryOperator, BinaryOperator> assignops;
	
	public AssignmentModifier(){
		assignops = new HashMap<BinaryOperator, BinaryOperator>();
		assignops.put(BinaryOperator.AMULT, BinaryOperator.MULT);
		assignops.put(BinaryOperator.ADIV, BinaryOperator.DIV);
		assignops.put(BinaryOperator.AMOD, BinaryOperator.MOD);
		assignops.put(BinaryOperator.APLUS, BinaryOperator.PLUS);
		assignops.put(BinaryOperator.AMINUS, BinaryOperator.MINUS);
		assignops.put(BinaryOperator.ABSLEFT, BinaryOperator.BSLEFT);
		assignops.put(BinaryOperator.ABSRIGHT, BinaryOperator.BSRIGHT);
		assignops.put(BinaryOperator.ABAND, BinaryOperator.BAND);
		assignops.put(BinaryOperator.ABXOR, BinaryOperator.BXOR);
		assignops.put(BinaryOperator.ABOR, BinaryOperator.BOR);
	}
	
	@Override
	public void visit(BinaryExpression be) {
		if (assignops.containsKey(be.operator)){
			AssignmentExpression assign  = new AssignmentExpression(be.left, new BinaryExpression(be.left, assignops.get(be.operator), be.right));
			result = assign;
			
		} else if(be.operator==BinaryOperator.ASSIG) {
			result = new AssignmentExpression(be.left, be.right);
		} else {
			result = be;
		}
	}

}
