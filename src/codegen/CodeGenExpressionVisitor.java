package codegen;

import types.PrimitiveType;
import types.Type;
import types.TypeClass;

import expression.AssignmentExpression;
import expression.CastExpression;
import expression.CommaExpression;
import expression.Expression;
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
	private String Typ = "";
//	private RegisterGenerator r;
//	private LabelGenerator l;
//	private OutputStreamWriter wr;
	private BlockCodeGenerator cg;
	private VisitPack pack;
	private boolean acces = false;//TODO je to premenna z pamate, ktoru treba po uprave ulozit
	private String adress = "";//TODO na tuto poziciu
	
	private void pis(CodeGenStream o,String s){
		o.writeLine(new String[]{s});
	}
	
	public CodeGenExpressionVisitor(VisitPack pack){
		this.pack=pack;
//		this.l=this.pack.l;
//		this.r=this.pack.r;
//		this.wr=this.pack.wr;
	}
	
	public String GetResultRegister(){
		return Register;
	}
	
	public String GetResultTyp(){
		return Typ;
	}

	public void writeAndOr(BinaryExpression e, boolean and) {
		String leftLabel = cg.getNextLabel();
		String rightLabel = cg.getNextLabel();
		String finalLabel = cg.getNextLabel();
		
		cg.str.writeLabel(leftLabel);
		String leftCompRes = cg.getNextregister();
		String left = cg.getExpressionRegister(e.left);
		cg.str.writeAssignment(leftCompRes, "icmp", "ne", cg.getExpressionTypeStr(e), left, ", 0");
		if(and)
			cg.str.writeLine("br", "i1", leftCompRes, ",", "label", rightLabel, ", label", finalLabel);
		else
			cg.str.writeLine("br", "i1", leftCompRes, ",", "label", finalLabel, ", label", rightLabel);
		cg.str.writeLabel(rightLabel);
		String right = cg.getExpressionRegister(e.right);
		String rightCompRes = cg.getNextregister();
		cg.str.writeAssignment(rightCompRes, "icmp", "ne", cg.getExpressionTypeStr(e), right, ", 0");
		cg.str.writeLabel(finalLabel);
		String tmp = cg.getNextregister();
		cg.str.writeAssignment(tmp, "phi", "i1", "[", leftCompRes, ",", leftLabel, "], [", rightCompRes, ",", rightLabel, "]");
		Register = cg.getNextregister();
		cg.str.writeAssignment(Register, "sext", "i1", tmp, "to", cg.getExpressionTypeStr(e));
	}
	
	@Override
	public void visit(BinaryExpression e) {
		Type t = cg.getExpressionType(e);
		assert t instanceof PrimitiveType;
		PrimitiveType pt = (PrimitiveType)t;
		String instruction = null;
		boolean relational = false;
		switch(e.operator) {
		case PLUS:
			instruction = pt.floating?"fadd":"add";
			break;
		case MINUS:
			instruction = pt.floating?"fsub":"sub";
			break;
		case MULT:
			instruction = pt.floating?"fmul":"mul";
			break;
		case DIV:
			if(pt.floating)
				instruction = "fdif";
			else
				instruction = pt.sign?"sdiv":"udiv";
			break;
		case MOD:
			instruction = pt.sign?"srem":"urem";
			break;
		case BSLEFT :
			instruction = "shl";
			break; // <<
		case BSRIGHT :
			instruction = "shr";
			break; // >>
		case BAND :
			instruction = "and";
			break; // &
		case BXOR :
			instruction = "xor";
			break; // ^
		case BOR :
			instruction = "or";
			break;  // |
		case AND :
			writeAndOr(e, true);
			return; // && //tu musi byt return, lebo sa k nim spravame uplne inak
		case OR :
			writeAndOr(e, false);
			return; // || //tu musi byt return, lebo sa k nim spravame uplne inak
		case GT :
			if(pt.floating)
				instruction = "fcmp ogt";
			else
				instruction = "icmp "+(pt.sign?"sgt":"ugt");
			relational = true;
			break; // >
		case EQ :
			instruction = pt.floating?"fcmp oeq":"icmp eq";
			relational = true;
			break; // ==
		default:
			assert false;
		}
		
		String tmp = cg.getNextregister();
		cg.str.writeAssignment(tmp, instruction, cg.getTypeString(pt), cg.getExpressionRegister(e.left), cg.getExpressionRegister(e.right));
		if(relational) {
			Register = cg.getNextregister();
			cg.str.writeAssignment(Register, "sext", "i1", tmp, "to", "i32");
		}
		else
			Register = tmp;
	}

	String writeIncDec(Expression e, boolean inc) {
		String tmp = cg.getNextregister();
		String instruction = inc?"add":"sub";
		Type t = cg.getExpressionType(e);
		assert t instanceof PrimitiveType;
		if(((PrimitiveType)t).floating) {
			instruction = "f"+instruction;
		}
		cg.str.writeAssignment(tmp, instruction, cg.getTypeString(t), cg.getExpressionRegister(e), "1");
		return tmp;
	}
	
	@Override
	public void visit(UnaryExpression e) {
		switch(e.op){
			case PRE_INC :
				Register = writeIncDec(e.exp, true);
				break; //++
			case PRE_DEC :
				Register = writeIncDec(e.exp, false);
				break; //--
			case POST_INC :
				Register = cg.getExpressionRegister(e.exp);
				writeIncDec(e.exp, true);
				break; //++
			case POST_DEC :
				Register = cg.getExpressionRegister(e.exp);
				writeIncDec(e.exp, false);
				break; //--
			case ADDR :
				Register = cg.getExpressionAddress(e.exp);
				break; //&
			case PTR :
				Register = cg.getNextregister();
				cg.str.writeAssignment(Register, "load", cg.getExpressionTypeStr(e.exp), cg.getExpressionRegister(e.exp));
				break; //*
			case COMP :
				Register=cg.getNextregister();
				pack.wr.writeAssignment(Register, "xor", cg.getExpressionTypeStr(e.exp), "-1", cg.getExpressionRegister(e.exp));
				break; //~ 
			case NOT :
				String tmp = cg.getNextregister();
				Type t = cg.getExpressionType(e.exp);
				assert t instanceof PrimitiveType;
				String instruction = ((PrimitiveType)t).floating?"fcmp one":"icmp ne";
				cg.str.writeAssignment(tmp, instruction, cg.getExpressionTypeStr(e.exp), cg.getExpressionRegister(e.exp), ",", "0");
				Register = cg.getNextregister();
				cg.str.writeAssignment(Register, "sext", tmp, "to", "i32");
				break; //!
		
		}

	}

	@Override
	public void visit(CastExpression e) {
		CodeGenExpressionVisitor v1 = new CodeGenExpressionVisitor(pack);
		e.exp.accept(v1);
		String result1 = v1.GetResultRegister();
		Type t = pack.t.getExpressionType(e);
		CodeGenTypeVisitor tv = new CodeGenTypeVisitor(pack);
		t.accept(tv);
		Typ=tv.GetTypeText();
		CodeGenTypeVisitor tv2 = new CodeGenTypeVisitor(pack);
		e.type.accept(tv2);
		String Typ2 = tv2.GetTypeText();
		Register = pack.r.next();
		pack.wr.writeAssignment(Register, "trunc",Typ, result1,",",Typ2);

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
		CodeGenExpressionVisitor v1 = new CodeGenExpressionVisitor(pack);
		e.exp.accept(v1);
		String result1 = v1.GetResultRegister();
		Type t = pack.t.getExpressionType(e);
		CodeGenTypeVisitor tv = new CodeGenTypeVisitor(pack);
		t.accept(tv);
		Typ=tv.GetTypeText();
		Register = pack.r.next();
		pack.wr.writeAssignment(Register, "getelementptr",Typ,"*",result1,",",e.id);

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
		Register = cg.getNextregister();
		cg.str.writeAssignment(Register, "load", cg.getExpressionTypeStr(e)+"*", cg.getIDAddress(e.id));
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

	private String getSizeExpr(Type t) {
		return ""; //TODO
	}
	
	@Override
	public void visit(AssignmentExpression e) {
		Type t = cg.getExpressionType(e);
		if(TypeClass.isStruct(t)) {
			String s = getSizeExpr(t);
			String laddr = cg.getExpressionAddress(e.left);
			String raddr = cg.getExpressionAddress(e.right);
			String ltmp = cg.getNextregister();
			String rtmp = cg.getNextregister();
			cg.str.writeAssignment(ltmp, "bitcast", cg.getTypeString(t)+"*", laddr, "to", "i8*");
			cg.str.writeAssignment(rtmp, "bitcast", cg.getTypeString(t)+"*", raddr, "to", "i8*");
			cg.str.writeLine("call", "void", "@llvm.memcpy.p0i8.p0i8.i64(i8*,", ltmp, ", i8*", rtmp, ", i64", s, ", i32 0, i1 false)");
		}
		else {
			cg.str.store(cg.getExpressionAddress(e.left), cg.getExpressionTypeStr(e), cg.getExpressionRegister(e.right));
		}
	}

}
