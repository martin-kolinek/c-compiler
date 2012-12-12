package codegen;

import expression.AssignmentExpression;
import expression.CastExpression;
import expression.CommaExpression;
import expression.ExpressionVisitor;
import expression.FunctionCallExpression;
import expression.IDExpression;
import expression.IndexingExpression;
import expression.MemberAccessExpression;
import expression.MemberDereferenceExpression;
import expression.SizeofExpression;
import expression.SizeofType;
import expression.TernaryExpression;
import expression.binop.BinaryExpression;
import expression.constant.CharConstantExpression;
import expression.constant.FloatConstantExpression;
import expression.constant.IntConstantExpression;
import expression.constant.StringConstantExpression;
import expression.unop.UnaryExpression;

public class ExpressionCodeGenerator implements ExpressionVisitor {
	
	private String Register;
	private String Typ;
	
	public String GetResultRegister(){//TODO
		return Register;
	}
	
	public String GetResultTyp(){//TODO
		return Typ;
	}

	@Override
	public void visit(BinaryExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(UnaryExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CastExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SizeofType e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SizeofExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MemberAccessExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MemberDereferenceExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IndexingExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IDExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IntConstantExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(FloatConstantExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StringConstantExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CharConstantExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(FunctionCallExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TernaryExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CommaExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AssignmentExpression e) {
		// TODO Auto-generated method stub
		
	}

}
