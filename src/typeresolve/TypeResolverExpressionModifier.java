package typeresolve;

import exceptions.SemanticException;
import expression.*;
import expression.binop.BinaryExpression;
import expression.constant.*;
import expression.unop.UnaryExpression;
import expression.unop.UnaryOperator;
import symbols.SymbolTable;
import transformers.ExpressionModifier;
import types.PointerType;
import types.PrimitiveType;
import types.Type;
import types.TypeClass;

public class TypeResolverExpressionModifier implements ExpressionModifier {

	private SymbolTable<Type> symb;
	private ExpressionTypeMapping mapping;
	public TypeResolverExpressionModifier(SymbolTable<Type> symb, ExpressionTypeMapping mp) {
		this.symb=symb;
		mapping=mp;
	}
	
	@Override
	public void visit(BinaryExpression e) {
		Type lt = mapping.getExpressionType(e.left);
		Type rt = mapping.getExpressionType(e.right);
		checkOperandTypes(e, lt, rt);
		Type retType = AutomaticConversions.getHigherType(lt, rt);
		e.left=AutomaticConversions.autoCast(e.left, retType, mapping);
		e.right=AutomaticConversions.autoCast(e.right, retType, mapping);
		switch(e.operator){
		case BAND:
		case BOR:
		case BSLEFT:
		case BSRIGHT:
		case BXOR:
		case MOD:
		case MINUS:
		case PLUS:
		case AND:
		case OR:
		case DIV:
		case MULT:
			mapping.setType(e, retType);
			break;
		case EQ:
		case NEQ:
		case LT:
		case LET:
		case GT:
		case GET:
			mapping.setType(e, PrimitiveType.INT);
		}
	}

	private void checkOperandTypes(BinaryExpression e, Type lt, Type rt) {
		switch(e.operator) {
		case BAND:
		case BOR:
		case BSLEFT:
		case BSRIGHT:
		case BXOR:
		case MOD:
			if(!TypeClass.isInteger(lt) || !TypeClass.isInteger(rt))
				throw new SemanticException("Only integral types allowed");
			break;
		case ABAND:
		case ABOR:
		case ABSLEFT:
		case ABSRIGHT:
		case ABXOR:
		case ADIV:
		case AMINUS:
		case AMOD:
		case AMULT:
		case APLUS:
		case ASSIG:
			assert false; //these should have been converted to AssignmentExpression
			break;
		case AND:
		case OR:
		case EQ:
		case NEQ:
		case MINUS:
		case PLUS:
		case LT:
		case LET:
		case GT:
		case GET:
			if(!TypeClass.isScalar(lt) || !TypeClass.isScalar(rt))
				throw new SemanticException("Expected scalar type");
			break;
		case DIV:
		case MULT:
			if(!TypeClass.isArithmethic(lt) || !TypeClass.isArithmethic(rt))
				throw new SemanticException("Expected arithmethic types");
			break;
		}
	}

	@Override
	public void visit(UnaryExpression e) {
		Type t = mapping.getExpressionType(e);
		assert t!=null;
		if((e.op==UnaryOperator.PRE_DEC 
				|| e.op==UnaryOperator.PRE_INC
				|| e.op==UnaryOperator.POST_DEC
				|| e.op==UnaryOperator.POST_INC
				|| e.op==UnaryOperator.NOT)
				&& !TypeClass.isScalar(t)) 
			throw new SemanticException("Prefix increment or decrement has invalid operand type");
		if((e.op==UnaryOperator.PLUS || e.op==UnaryOperator.MINUS) && !TypeClass.isArithmethic(t))
			throw new SemanticException("Unary +,- not having arithmetic operand");
		if(e.op==UnaryOperator.COMP && !TypeClass.isInteger(t))
			throw new SemanticException("Complement not having integer operand");
		if(e.op==UnaryOperator.PTR){
			if(!TypeClass.isPointer(t))
				throw new SemanticException("Dereference of non pointer type");
			mapping.setType(e, ((PointerType)t).pointedToType);
		}
		else if(e.op==UnaryOperator.ADDR)
			mapping.setType(e, new PointerType(t));
		else
			mapping.setType(e, t);
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
		Type t = symb.get(e.id);
		if(t==null)
			throw new SemanticException("Identifier not declared");
		mapping.setType(e, t);
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
	public Expression getResult() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
