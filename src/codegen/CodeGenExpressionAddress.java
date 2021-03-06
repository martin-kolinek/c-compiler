package codegen;

import astnode.ASTNode;
import types.StructType;
import types.TypeClass;
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
import expression.constant.FloatConstantExpression;
import expression.constant.IntConstantExpression;
import expression.constant.StringConstantExpression;
import expression.unop.UnaryExpression;

public class CodeGenExpressionAddress implements ExpressionVisitor {

	public CodeGenExpressionAddress(BlockCodeGenerator gen) {
		cg=gen;
	}
	
	private String result;
	private BlockCodeGenerator cg;
	
	private void fail(ASTNode pos){
		throw new SemanticException("Unable to get address of this expression", pos);
	}
	
	@Override
	public void visit(BinaryExpression e) {
		fail(e);
	}

	@Override
	public void visit(UnaryExpression e) {
		switch(e.op){
		case PTR:
			result = cg.getExpressionRegister(e.exp);
			break;
		default:
			fail(e);
		}
	}

	@Override
	public void visit(CastExpression e) {
		fail(e);
	}

	@Override
	public void visit(SizeofType e) {
		fail(e);
	}

	@Override
	public void visit(SizeofExpression e) {
		fail(e);
	}
	
	@Override
	public void visit(MemberAccessExpression e) {
		result = cg.getNextregister();
		String addr = cg.getExpressionAddress(e.exp);
		StructType str = (StructType)cg.getExpressionType(e.exp);
		String addition = "";
		int pos = str.getMemberPosition(e.id);
		if(TypeClass.isArray(str.members.get(pos).type))
			addition = ", i32 0";
		cg.str.writeAssignment(result, "getelementptr", cg.getTypeString(str)+"*", addr, ", i32 0,", "i32", Integer.toString(pos), addition);
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
		result = cg.getIDAddress(e.id);
		assert result!=null;
	}

	@Override
	public void visit(IntConstantExpression e) {
		fail(e);
	}

	@Override
	public void visit(FloatConstantExpression e) {
		fail(e);
	}

	@Override
	public void visit(StringConstantExpression e) {
		fail(e);
	}

	@Override
	public void visit(FunctionCallExpression e) {
		fail(e);
	}

	@Override
	public void visit(TernaryExpression e) {
		fail(e);
	}

	@Override
	public void visit(CommaExpression e) {
		fail(e);
	}

	@Override
	public void visit(AssignmentExpression e) {
		fail(e);
	}

	public String getResult() {
		return result;
	}
}
