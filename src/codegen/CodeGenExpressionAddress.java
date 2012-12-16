package codegen;

import symbols.SymbolTable;
import typeresolve.ExpressionTypeMapping;
import types.StructType;
import exceptions.SemanticException;
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

public class CodeGenExpressionAddress implements ExpressionVisitor {

	private ExpressionValueMapping valmap;
	private ExpressionTypeMapping typemap;
	private SymbolTable<String> idAddr;
	private VisitPack pack;
	private String result;
	private CodeGenerator cg;
	
	private void fail(){
		throw new SemanticException("Unable to get address of this expression");
	}
	
	@Override
	public void visit(BinaryExpression e) {
		fail();
	}

	@Override
	public void visit(UnaryExpression e) {
		switch(e.op){
		case PTR:
			result = valmap.getExpressionResult(e.exp);
		default:
			fail();
		}
	}

	@Override
	public void visit(CastExpression e) {
		fail();
	}

	@Override
	public void visit(SizeofType e) {
		fail();
	}

	@Override
	public void visit(SizeofExpression e) {
		fail();
	}

	private int getMemberPosition(StructType str, String member) {
		for(int i=0; i<str.members.size(); i++) {
			if(str.members.get(i).identifier.equals(member))
				return i;
		}
		assert false;
		return 0;
	}
	
	@Override
	public void visit(MemberAccessExpression e) {
		result = pack.r.next();
		StructType str = (StructType)typemap.getExpressionType(e.exp);
		pack.wr.writeAssignment(result, "getelementptr", cg.getTypeString(str)+"*,", "i32 0,", "i32", Integer.toString(getMemberPosition(str, e.id)));
	}

	@Override
	public void visit(MemberDereferenceExpression e) {
		assert false;
	}

	@Override
	public void visit(IndexingExpression e) {
		assert false;
	}

	@Override
	public void visit(IDExpression e) {
		result = idAddr.get(e.id);
		assert result!=null;
	}

	@Override
	public void visit(IntConstantExpression e) {
		fail();
	}

	@Override
	public void visit(FloatConstantExpression e) {
		fail();
	}

	@Override
	public void visit(StringConstantExpression e) {
		fail();
	}

	@Override
	public void visit(CharConstantExpression e) {
		fail();
	}

	@Override
	public void visit(FunctionCallExpression e) {
		fail();
	}

	@Override
	public void visit(TernaryExpression e) {
		fail();
	}

	@Override
	public void visit(CommaExpression e) {
		fail();
	}

	@Override
	public void visit(AssignmentExpression e) {
		fail();
	}

}
