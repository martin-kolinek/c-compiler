package codegen;

import typeresolve.AutomaticConversions;
import types.ArrayType;
import types.PointerType;
import types.PrimitiveType;
import types.StructType;
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
import expression.constant.FloatConstantExpression;
import expression.constant.IntConstantExpression;
import expression.constant.StringConstantExpression;
import expression.unop.UnaryExpression;

public class CodeGenExpressionVisitor implements ExpressionVisitor {
	
	private String Register;
	private BlockCodeGenerator cg;
	public CodeGenExpressionVisitor(BlockCodeGenerator cg){
		this.cg=cg;
	}
	
	public String GetResultRegister(){
		return Register;
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
		cg.str.writeAssignment(Register, "zext", "i1", tmp, "to", cg.getExpressionTypeStr(e));
	}
	
	@Override
	public void visit(BinaryExpression e) {
		Type t = cg.getExpressionType(e.left);
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
				instruction = "fdiv";
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
			instruction = pt.sign?"ashr":"lshr";
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
		cg.str.writeAssignment(tmp, instruction, cg.getTypeString(pt), cg.getExpressionRegister(e.left), ",", cg.getExpressionRegister(e.right));
		if(relational) {
			Register = cg.getNextregister();
			cg.str.writeAssignment(Register, "zext", "i1", tmp, "to", "i32");
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
		cg.str.writeAssignment(tmp, instruction, cg.getTypeString(t), cg.getExpressionRegister(e), ",", "1");
		cg.str.store(cg.getExpressionAddress(e), cg.getExpressionTypeStr(e), tmp);
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
				cg.str.writeAssignment(Register, "xor", cg.getExpressionTypeStr(e.exp), "-1, ", cg.getExpressionRegister(e.exp));
				break; //~ 
			case NOT :
				String tmp = cg.getNextregister();
				Type t = cg.getExpressionType(e.exp);
				assert t instanceof PrimitiveType;
				String instruction = ((PrimitiveType)t).floating?"fcmp oeq":"icmp eq";
				cg.str.writeAssignment(tmp, instruction, cg.getExpressionTypeStr(e.exp), cg.getExpressionRegister(e.exp), ",", "0");
				Register = cg.getNextregister();
				cg.str.writeAssignment(Register, "zext", "i1", tmp, "to", "i32");
				break; //!
		
		}

	}

	@Override
	public void visit(CastExpression e) {
		Type orig = cg.getExpressionType(e.exp);
		Type newt = e.type;
		Register=cg.getExpressionRegister(e.exp);
		if(orig instanceof ArrayType) {
			orig=AutomaticConversions.arrayToPtr(orig);
		}
		if(orig instanceof PointerType) {
			String res = cg.getNextregister();
			cg.str.writeAssignment(res, "ptrtoint", cg.getTypeString(orig), Register, "to", cg.getTypeString(PrimitiveType.LONG));
			Register = res;
			orig = PrimitiveType.LONG;
		}
		if(newt instanceof PointerType) {
			newt = PrimitiveType.LONG;
		}
		if(orig instanceof PrimitiveType && newt instanceof PrimitiveType && orig!=newt) {
			PrimitiveType porig = (PrimitiveType)orig;
			PrimitiveType pnew = (PrimitiveType)e.type;
			String instruction = null;
			if(porig.floating && pnew.floating) {
				instruction = porig.size>pnew.size?"fptrunc":"fpext";
			}
			else if(porig.floating) {
				instruction = pnew.sign?"fptosi":"fptoui";
			}
			else if(pnew.floating) {
				instruction = porig.sign?"sitofp":"uitofp";
			}
			else if(porig.size<pnew.size) {
				instruction = pnew.sign?"sext":"zext";
			}
			else {
				instruction = "trunc";
			}
			String tmp = cg.getNextregister();
			cg.str.writeAssignment(tmp, instruction, cg.getTypeString(orig), Register, "to", cg.getTypeString(pnew));
			Register = tmp;
		}
		if(newt!=e.type) {
			assert TypeClass.isPointer(e.type);
			assert newt == PrimitiveType.LONG;
			String res = cg.getNextregister();
			cg.str.writeAssignment(res, "inttoptr", cg.getTypeString(PrimitiveType.LONG), Register, "to", cg.getTypeString(e.type));
			Register = res;
		}
	}

	@Override
	public void visit(SizeofType e) {
		Register = cg.getSizeOfResult(e.type);
	}

	@Override
	public void visit(SizeofExpression e) {
		Register = cg.getSizeOfResult(cg.getExpressionType(e.exp));
	}

	@Override
	public void visit(MemberAccessExpression e) {
		String sptr = cg.getExpressionAddress(e.exp);
		String ptr = cg.getNextregister();
		Type t = cg.getExpressionType(e.exp);
		assert t instanceof StructType;
		int pos = ((StructType)t).getMemberPosition(e.id);
		String addition = "";
		boolean array = TypeClass.isArray(((StructType)t).members.get(pos).type);
		if(array)
			addition = ", i32 0";
		cg.str.writeAssignment(ptr, "getelementptr", cg.getTypeString(t)+"*", sptr, ", i32 0, i32", Integer.toString(pos), addition);
		if(array)
			Register = ptr;
		else {
			Register = cg.getNextregister();
			cg.str.writeAssignment(Register, "load", cg.getExpressionTypeStr(e)+"*", ptr);
		}
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
		Register = Long.toString(e.value);
	}

	@Override
	public void visit(FloatConstantExpression e) {
		double val = e.value;
		if(cg.getExpressionType(e)==PrimitiveType.FLOAT)
			val=(float)val;
		Register = Double.toString(val);
	}

	@Override
	public void visit(StringConstantExpression e) {
		String addr = cg.getStringAddress(e.value);
		Register = cg.getNextregister();
		cg.str.writeAssignment(Register, "getelementptr", cg.getProperTypeString(cg.getExpressionType(e))+"*", addr, ", i32 0, i32 0");
	}

	@Override
	public void visit(FunctionCallExpression e) {
		Register = cg.getNextregister();
		StringBuilder args = new StringBuilder();
		boolean first = true;
		for(Expression a : e.args) {
			if(!first) {
				args.append(", ");
			}
			first = false;
			args.append(cg.getExpressionTypeStr(a));
			args.append(" ");
			args.append(cg.getExpressionRegister(a));
		}
		if(e.func.returnType==PrimitiveType.VOID)
			cg.str.writeLine("call", cg.generateFunctionType(e.func) , "@"+e.name, "(", args.toString(), ")");
		else
			cg.str.writeAssignment(Register, "call", cg.generateFunctionType(e.func) , "@"+e.name, "(", args.toString(), ")");
	}

	@Override
	public void visit(TernaryExpression e) {
		String trueLabel = cg.getNextLabel();
		String falseLabel = cg.getNextLabel();
		String jmpLabel = cg.getNextLabel();
		String condRes = cg.getNextregister();
		cg.str.writeAssignment(condRes, "icmp ne", cg.getExpressionTypeStr(e.condition), cg.getExpressionRegister(e.condition),  ", 0");
		cg.str.writeLine("br i1", condRes, "label", trueLabel, ", label", falseLabel);
		cg.str.writeLabel(trueLabel);
		String trueRes = cg.getExpressionRegister(e.ontrue);
		cg.str.writeLine("br label", jmpLabel);
		cg.str.writeLabel(falseLabel);
		String falseRes = cg.getExpressionRegister(e.onfalse);
		cg.str.writeLine("br label", jmpLabel);
		cg.str.writeLabel(jmpLabel);
		Register = cg.getNextregister();
		cg.str.writeAssignment(Register, "phi", cg.getExpressionTypeStr(e), "[", trueRes, ",", trueLabel, "], [", falseRes, ",", falseLabel, "]");
	}

	@Override
	public void visit(CommaExpression e) {
		for(Expression ee:e.expressions) {
			Register = cg.getExpressionRegister(ee);
		}
	}

	@Override
	public void visit(AssignmentExpression e) {
		cg.str.store(cg.getExpressionAddress(e.left), cg.getExpressionTypeStr(e), cg.getExpressionRegister(e.right));
	}

}
