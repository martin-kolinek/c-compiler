package transformers;

import expression.*;
import expression.binop.*;
import expression.constant.*;
import expression.unop.UnaryExpression;
import expression.unop.UnaryOperator;

public class ConstantExpressionModifier extends EmptyExpressionModifier{

	private boolean isConstant(Expression e){
		return ((e instanceof IntConstantExpression)||(e instanceof FloatConstantExpression));
	}
	
	public void visit(BinaryExpression e){
		if(isConstant(e.left) && isConstant(e.right)){
			if(e.operator == BinaryOperator.PLUS){
				if((e.left instanceof FloatConstantExpression)||(e.right instanceof FloatConstantExpression)){
					result = new FloatConstantExpression(((ConstantExpression)e.left).getNumericValue() + ((ConstantExpression)e.right).getNumericValue());
				} else {
					result = new IntConstantExpression(((ConstantExpression)e.left).getNumericValue() + ((ConstantExpression)e.right).getNumericValue());
				}
				return;
			} else if(e.operator == BinaryOperator.MINUS){
				if((e.left instanceof FloatConstantExpression)||(e.right instanceof FloatConstantExpression)){
					result = new FloatConstantExpression(((ConstantExpression)e.left).getNumericValue() - ((ConstantExpression)e.right).getNumericValue());
				} else {
					result = new IntConstantExpression(((ConstantExpression)e.left).getNumericValue() - ((ConstantExpression)e.right).getNumericValue());
				}
				return;
			} else if(e.operator == BinaryOperator.MULT){
				if((e.left instanceof FloatConstantExpression)||(e.right instanceof FloatConstantExpression)){
					result = new FloatConstantExpression(((ConstantExpression)e.left).getNumericValue() * ((ConstantExpression)e.right).getNumericValue());
				} else {
					result = new IntConstantExpression(((ConstantExpression)e.left).getNumericValue() * ((ConstantExpression)e.right).getNumericValue());
				}
				return;
			} else if(e.operator == BinaryOperator.DIV){
				if((e.left instanceof FloatConstantExpression)||(e.right instanceof FloatConstantExpression)){
					result = new FloatConstantExpression(((ConstantExpression)e.left).getNumericValue() / ((ConstantExpression)e.right).getNumericValue());
				} else {
					result = new IntConstantExpression(((ConstantExpression)e.left).getNumericValue() / ((ConstantExpression)e.right).getNumericValue());
				}
				return;
			} else if(e.operator == BinaryOperator.MOD){
				result = new IntConstantExpression(Math.round(((ConstantExpression)e.left).getNumericValue()) % Math.round(((ConstantExpression)e.right).getNumericValue()));
				return;
			} else if(e.operator == BinaryOperator.BSLEFT){
				result = new IntConstantExpression(Math.round(((ConstantExpression)e.left).getNumericValue()) << Math.round(((ConstantExpression)e.right).getNumericValue()));
				return;
			} else if(e.operator == BinaryOperator.BSRIGHT){
				result = new IntConstantExpression(Math.round(((ConstantExpression)e.left).getNumericValue()) >> Math.round(((ConstantExpression)e.right).getNumericValue()));
				return;
			} else if(e.operator == BinaryOperator.BAND){
				result = new IntConstantExpression(Math.round(((ConstantExpression)e.left).getNumericValue()) & Math.round(((ConstantExpression)e.right).getNumericValue()));
				return;
			} else if(e.operator == BinaryOperator.BXOR){
				result = new IntConstantExpression(Math.round(((ConstantExpression)e.left).getNumericValue()) ^ Math.round(((ConstantExpression)e.right).getNumericValue()));
				return;
			} else if(e.operator == BinaryOperator.BOR){
				result = new IntConstantExpression(Math.round(((ConstantExpression)e.left).getNumericValue()) | Math.round(((ConstantExpression)e.right).getNumericValue()));
				return;
			} else if(e.operator == BinaryOperator.AND){
				result = new IntConstantExpression(((((ConstantExpression)e.left).getNumericValue()!=0) && (((ConstantExpression)e.right).getNumericValue()!=0)) ? 1 : 0);
				return;
			} else if(e.operator == BinaryOperator.OR){
				result = new IntConstantExpression(((((ConstantExpression)e.left).getNumericValue()!=0) || (((ConstantExpression)e.right).getNumericValue()!=0)) ? 1 : 0);
				return;
			} else if(e.operator == BinaryOperator.GT){
				result = new IntConstantExpression((((ConstantExpression)e.left).getNumericValue() > ((ConstantExpression)e.right).getNumericValue()) ? 1 : 0);
				return;
			} else if(e.operator == BinaryOperator.LT){
				result = new IntConstantExpression((((ConstantExpression)e.left).getNumericValue() < ((ConstantExpression)e.right).getNumericValue()) ? 1 : 0);
				return;
			} else if(e.operator == BinaryOperator.GET){
				result = new IntConstantExpression((((ConstantExpression)e.left).getNumericValue() >= ((ConstantExpression)e.right).getNumericValue()) ? 1 : 0);
				return;
			} else if(e.operator == BinaryOperator.LET){
				result = new IntConstantExpression((((ConstantExpression)e.left).getNumericValue() <= ((ConstantExpression)e.right).getNumericValue()) ? 1 : 0);
				return;
			} else if(e.operator == BinaryOperator.EQ){
				result = new IntConstantExpression((((ConstantExpression)e.left).getNumericValue() == ((ConstantExpression)e.right).getNumericValue()) ? 1 : 0);
				return;
			} else if(e.operator == BinaryOperator.NEQ){
				result = new IntConstantExpression((((ConstantExpression)e.left).getNumericValue() != ((ConstantExpression)e.right).getNumericValue()) ? 1 : 0);
				return;
			} else {
				result = e;
			}
		} else
			result = e;
	}
	
	public void visit(UnaryExpression e){
		if (isConstant(e.exp)){
			if (e.op == UnaryOperator.ADDR){
				result = e;
				return;
			} else if (e.op == UnaryOperator.PTR){
				result = e;
				return;
			} else if (e.op == UnaryOperator.PLUS){
				result = e.exp;
				return;
			} else if (e.op == UnaryOperator.MINUS){
				if (e.exp instanceof IntConstantExpression){
					result = new IntConstantExpression(-((IntConstantExpression)e.exp).value);
					return;
				} else {
					result = new FloatConstantExpression(-((FloatConstantExpression)e.exp).value);
					return;
				}
			} else if (e.op == UnaryOperator.COMP){
				result = new IntConstantExpression(~((IntConstantExpression)e.exp).value);
				return;
			} else if (e.op == UnaryOperator.NOT){
				result = new IntConstantExpression((((ConstantExpression)e.exp).getNumericValue() == 0) ? 1 : 0);
				return;
			} else {
				result = e;
			}
		} else 
			result = e;
	}
	
	public void visit(CastExpression e){
		//@TODO: iba mozno, nemyslim si ze to bude potrebne
		super.visit(e);
	}
	public void visit(SizeofType e){
		//@TODO: iba mozno, nemyslim si ze to bude potrebne
		super.visit(e);
	}
	public void visit(SizeofExpression e){
		//@TODO: iba mozno, nemyslim si ze to bude potrebne
		super.visit(e);
	}
	
	public void visit(TernaryExpression e){
		if (isConstant(e.condition)){
			if (((ConstantExpression)e.condition).getNumericValue()!=0)
				result = e.ontrue;
			else
				result = e.onfalse;
			return;
		} else {
			result = e;
		}
	}
	
}
