package codegen;

import java.io.OutputStreamWriter;

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

public class CodeGenExpressionVisitor implements ExpressionVisitor {
	
	private String Register;
	private String Typ;
	private RegisterGenerator r;
	private LabelGenerator l;
	private OutputStreamWriter wr;
	private VisitPack pack;
	
	public CodeGenExpressionVisitor(VisitPack pack){
		this.pack=pack;
		this.l=this.pack.l;
		this.r=this.pack.r;
		this.wr=this.pack.wr;
	}
	
	public String GetResultRegister(){//TODO
		return Register;
	}
	
	public String GetResultTyp(){//TODO
		return Typ;
	}

	@Override
	public void visit(BinaryExpression e) {
		// TODO Auto-generated method stub
		
		CodeGenExpressionVisitor v1 = new CodeGenExpressionVisitor(pack);
		CodeGenExpressionVisitor v2 = new CodeGenExpressionVisitor(pack);
		e.right.accept(v1);
		e.left.accept(v2);
		String result1 = v1.GetResultRegister();
		String result2 = v2.GetResultRegister();
		String typ1 = v1.GetResultTyp();
		String typ2 = v2.GetResultTyp();

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
