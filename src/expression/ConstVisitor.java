package expression;

import toplevel.InBlock;
import types.ConstTypeSizeVisitor;
import types.ExpressionTypeMapping;
import types.Type;
import expression.binop.BinaryExpression;
import expression.constant.CharConstantExpression;
import expression.constant.FloatConstantExpression;
import expression.constant.IntConstantExpression;
import expression.constant.StringConstantExpression;
import expression.unop.UnaryExpression;

public class ConstVisitor implements ExpressionVisitor {
	
	private boolean constant;
	private ExpressionTypeMapping etypes;
	
	public boolean expIsConstant(){
		return constant;
	}
	
	public ConstVisitor(ExpressionTypeMapping mp){
		this.constant = true; //set false on non-constant
		etypes = mp;
	}

	@Override
	public void visit(BinaryExpression binaryExpression) {
		binaryExpression.left.accept(this);
		binaryExpression.right.accept(this);
	}

	@Override
	public void visit(UnaryExpression unaryExpression) { 
		unaryExpression.exp.accept(this);
	}

	@Override
	public void visit(CastExpression castExpression) {
		castExpression.exp.accept(this);
		
	}

	private void sizeOfTypeConst(InBlock t) {
		ConstTypeSizeVisitor cv = new ConstTypeSizeVisitor(etypes);
		t.accept(cv);
		if(!cv.isConstant())
			constant = false;
	}
	
	@Override
	public void visit(SizeofType sizeofType) {
		sizeOfTypeConst(sizeofType.typedecl);
	}

	@Override
	public void visit(SizeofExpression sizeofExpression) {
		Type t = etypes.getExpressionType(sizeofExpression.exp);
		sizeOfTypeConst(t);
	}

	@Override
	public void visit(MemberAccessExpression memberAccessExpression) {
		constant = false; //there is no constant struct;
	}

	@Override
	public void visit(MemberDereferenceExpression memberDereferenceExpression) {
		constant = false; //there is no constant struct;
	}

	@Override
	public void visit(IndexingExpression indexingExpression) {
		constant = false; //there cannot be a constant array;
	}

	@Override
	public void visit(IDExpression idExpression) {
		this.constant = false; //vzdycky? jedina vynimka su enumeration konstanty, ale to by uz malo byt nahradene intami v tejto chvili
	}

	@Override
	public void visit(IntConstantExpression intConstantExpression) {
		//ok, je konstanta
	}

	@Override
	public void visit(FloatConstantExpression floatConstantExpression) {
		//ok, je konstanta
	}

	@Override
	public void visit(StringConstantExpression stringConstantExpression) {
		this.constant = false; //nevieme kde konkretne v pamati bude a teda nie je to konstanta
	}

	@Override
	public void visit(CharConstantExpression charConstantExpression) {
		//ok, je konstanta
	}

	@Override
	public void visit(FunctionCallExpression functionCallExpression) {
		this.constant = false;
	}

}
