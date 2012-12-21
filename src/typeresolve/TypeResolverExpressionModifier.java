package typeresolve;

import declaration.ResolvedDeclaration;
import exceptions.SemanticException;
import expression.*;
import expression.binop.BinaryExpression;
import expression.binop.BinaryOperator;
import expression.constant.*;
import expression.unop.UnaryExpression;
import expression.unop.UnaryOperator;
import symbols.SymbolTable;
import toplevel.FunctionDefinition;
import transformers.ExpressionModifier;
import types.ArrayType;
import types.PointerType;
import types.PrimitiveType;
import types.StructType;
import types.Type;
import types.TypeClass;

public class TypeResolverExpressionModifier implements ExpressionModifier {

	private SymbolTable<Type> symb;
	private SymbolTable<FunctionDefinition> funcs;
	private ExpressionTypeMapping mapping;
	private Expression result;
	public TypeResolverExpressionModifier(SymbolTable<Type> symb, SymbolTable<FunctionDefinition> funcs, ExpressionTypeMapping mp) {
		this.symb=symb;
		this.funcs=funcs;
		mapping=mp;
	}
	

	@Override
	public void visit(AssignmentExpression e) {
		Type lt = mapping.getExpressionType(e.left);
		Type rt = mapping.getExpressionType(e.right);
		if(lt == rt) {
			mapping.setType(e, lt);
		}
		else if(TypeClass.isScalar(lt) && TypeClass.isScalar(rt)) {
			if(TypeClass.isArray(lt))
				throw new SemanticException("Unable to assign to array");
			e.right=AutomaticConversions.autoCast(e.right, lt, mapping);
			mapping.setType(e,  lt);
		}
		else {
			throw new SemanticException("Wrong types in assignment");
		}
		result = e;
	}
	
	private Expression createSizeOf(Type t) {
		assert t instanceof PointerType;
		SizeofType soex = new SizeofType();
		soex.type=((PointerType)t).pointedToType;
		mapping.setType(soex, PrimitiveType.INT);
		return AutomaticConversions.autoCast(soex, PrimitiveType.LONG, mapping);
	}
	
	@Override
	public void visit(BinaryExpression e) {
		Type lt = mapping.getExpressionType(e.left);
		Type rt = mapping.getExpressionType(e.right);
		checkOperandTypes(e, lt, rt);
		
		switch(e.operator){
		case MINUS:
		case PLUS:
			if(TypeClass.isPointerOrArray(lt) || TypeClass.isPointerOrArray(rt)) {
				e.left = AutomaticConversions.autoCast(e.left, PrimitiveType.LONG, mapping);
				e.right = AutomaticConversions.autoCast(e.right, PrimitiveType.LONG, mapping);
				mapping.setType(e, PrimitiveType.LONG);
				if(TypeClass.isPointerOrArray(lt) && TypeClass.isPointerOrArray(rt) && e.operator == BinaryOperator.MINUS) {
					PointerType plt = AutomaticConversions.arrayToPtr(lt);
					Expression so = createSizeOf(plt.pointedToType);
					result = new BinaryExpression(e, BinaryOperator.DIV, so);
					mapping.setType(result, PrimitiveType.LONG);
				}
				else if(TypeClass.isPointerOrArray(lt)) {
					PointerType plt = AutomaticConversions.arrayToPtr(lt);
					lt = plt;
					e.right = new BinaryExpression(e.right, BinaryOperator.MULT, createSizeOf(plt.pointedToType));
					mapping.setType(e.right, PrimitiveType.LONG);
					result = AutomaticConversions.autoCast(e, lt, mapping);
				}
				else {
					PointerType prt = AutomaticConversions.arrayToPtr(rt);
					rt = prt;
					e.left = new BinaryExpression(e.left, BinaryOperator.MULT, createSizeOf(prt.pointedToType));
					result = AutomaticConversions.autoCast(e, rt, mapping);
				}
			}
			else {
				handleBasicBinaryExp(e, lt, rt);
			}
			break;
		case BAND:
		case BOR:
		case BSLEFT:
		case BSRIGHT:
		case BXOR:
		case MOD:
		case DIV:
		case MULT:
			handleBasicBinaryExp(e, lt, rt);
			break;
		case AND:
		case OR:
		case EQ:
		case NEQ:
		case LT:
		case LET:
		case GT:
		case GET:
			Type common = AutomaticConversions.getHigherType(lt, rt);
			e.left=AutomaticConversions.autoCast(e.left, common, mapping);
			e.right=AutomaticConversions.autoCast(e.right, common, mapping);
			mapping.setType(e, PrimitiveType.INT);
			result=e;
			break;
		}
	}

	private void handleBasicBinaryExp(BinaryExpression e, Type lt, Type rt) {
		Type retType = AutomaticConversions.getHigherType(lt, rt);
		e.left=AutomaticConversions.autoCast(e.left, retType, mapping);
		e.right=AutomaticConversions.autoCast(e.right, retType, mapping);
		mapping.setType(e, retType);
		result = e;
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
		Type t = mapping.getExpressionType(e.exp);
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
			if(!TypeClass.isPointerOrArray(t))
				throw new SemanticException("Dereference of non pointer type");
			e.exp=AutomaticConversions.arrayToPtr(e.exp, mapping);
			t=mapping.getExpressionType(e.exp);
			mapping.setType(e, ((PointerType)t).pointedToType);
		}
		else if(e.op==UnaryOperator.ADDR)
			mapping.setType(e, new PointerType(t));
		else if(TypeClass.isPointerOrArray(t)) {
			Expression mid = AutomaticConversions.autoCast(e.exp, PrimitiveType.LONG, mapping);
			Type ptr = AutomaticConversions.arrayToPtr(t);
			result = new CastExpression(mid, ptr);
			mapping.setType(e, ptr);
		}
		else if(e.op==UnaryOperator.NOT)
			mapping.setType(e, PrimitiveType.INT);
		else
			mapping.setType(e, t);
		result = e;
	}

	@Override
	public void visit(CastExpression e) {
		if(!TypeClass.isScalar(e.type) || !TypeClass.isScalar(mapping.getExpressionType(e.exp))){
			throw new SemanticException("Casts support only scalar types");
		}
		if(TypeClass.isArray(e.type))
			throw new SemanticException("Unable to cast to array");
		e.exp=AutomaticConversions.autoCast(e.exp, e.type, mapping);
		mapping.setType(e, e.type);
		result = e;
	}

	@Override
	public void visit(SizeofType e) {
		mapping.setType(e, PrimitiveType.INT);
		result = e;
	}

	@Override
	public void visit(SizeofExpression e) {
		mapping.setType(e, PrimitiveType.INT);
		result = e;
	}

	@Override
	public void visit(MemberAccessExpression e) {
		Type t = mapping.getExpressionType(e.exp);
		assert t!=null;
		if(!TypeClass.isStruct(t))
			throw new SemanticException("Only structs have members");
		StructType st = (StructType)t;
		ResolvedDeclaration m = st.getMember(e.id);
		if(m==null)
			throw new SemanticException("No such member");
		mapping.setType(e, m.type);
		result = e;
	}

	@Override
	public void visit(MemberDereferenceExpression e) {
		assert false; //these should have been removed by now
	}

	@Override
	public void visit(IndexingExpression e) {
		Type tt = mapping.getExpressionType(e.target);
		Type it = mapping.getExpressionType(e.index);
		if(!TypeClass.isInteger(it))
			throw new SemanticException("Index in indexing expression must be integer");
		if(!TypeClass.isPointerOrArray(tt))
			throw new SemanticException("Target of indexation must be array or pointer");
		PointerType ptr = AutomaticConversions.arrayToPtr(tt);
		e.target = AutomaticConversions.arrayToPtr(e.target, mapping);
		mapping.setType(e, ptr.pointedToType);
		assert false; //these should have been removed by now
	}

	@Override
	public void visit(IDExpression e) {
		Type t = symb.get(e.id);
		if(t==null)
			throw new SemanticException("Identifier not declared");
		mapping.setType(e, t);
		result = e;
	}

	@Override
	public void visit(IntConstantExpression e) {
		mapping.setType(e, PrimitiveType.INT);
		result = e;
	}

	@Override
	public void visit(FloatConstantExpression e) {
		mapping.setType(e, PrimitiveType.INT);
		result = e;
		
	}

	@Override
	public void visit(StringConstantExpression e) {
		mapping.setType(e, new ArrayType(PrimitiveType.CHAR, new IntConstantExpression(e.value.length)));
		result = e;
	}

	@Override
	public void visit(FunctionCallExpression e) {
		FunctionDefinition f = funcs.get(e.name);
		if(f==null) {
			throw new SemanticException("Call to unknown function");
		}
		if((!f.variadic && e.args.size()!=f.parameters.size()) || e.args.size()<f.parameters.size())
			throw new SemanticException("Function call with wrong number of parameters");
		for(int i=0; i<f.parameters.size(); i++){
			Type t =f.parameters.get(i).type; 
			assert !TypeClass.isArray(t);
			if(TypeClass.isStruct(t)) {
				Type pt = mapping.getExpressionType(e.args.get(i));
				if(t!=pt)
					throw new SemanticException("Wrong parameter type");
				Expression newExp = new UnaryExpression(e.args.get(i), UnaryOperator.ADDR);
				mapping.setType(newExp, new PointerType(t));
				e.args.set(i, newExp);
			}
			else {
				e.args.set(i, AutomaticConversions.autoCast(e.args.get(i), t, mapping));
			}
		}
		mapping.setType(e, f.returnType);
		e.func=f;
		result = e;
	}

	@Override
	public void visit(TernaryExpression e) {
		if(!TypeClass.isScalar(mapping.getExpressionType(e.condition)))
			throw new SemanticException("Condition in ternary operator needs to be arithmetic");
		e.condition=AutomaticConversions.autoCast(e.condition, PrimitiveType.LONG, mapping);
		Type lt = mapping.getExpressionType(e.ontrue);
		Type rt = mapping.getExpressionType(e.onfalse);
		if(TypeClass.isArithmethic(lt) && TypeClass.isArithmethic(rt)) {
			Type common = AutomaticConversions.getHigherType(lt, rt);
			e.ontrue = AutomaticConversions.autoCast(e.ontrue, common, mapping);
			e.onfalse = AutomaticConversions.autoCast(e.onfalse, common, mapping);
			mapping.setType(e, common);
			result = e;
			return;
		}
		if(TypeClass.isPointerOrArray(lt) && TypeClass.isPointerOrArray(rt)) {
			Type resultType = AutomaticConversions.arrayToPtr(lt);
			e.ontrue = AutomaticConversions.autoCast(e.ontrue, PrimitiveType.LONG, mapping);
			e.onfalse = AutomaticConversions.autoCast(e.onfalse, PrimitiveType.LONG, mapping);
			mapping.setType(e, PrimitiveType.LONG);
			result = AutomaticConversions.autoCast(e, resultType, mapping);
			return;
		}
		if(lt==rt) {
			mapping.setType(e, lt);
			result = e;
			return;
		}
		throw new SemanticException("Ternary operator options have incompatible types");
	}

	@Override
	public Expression getResult() {
		return result;
	}

	@Override
	public void visit(CommaExpression e) {
		mapping.setType(e, mapping.getExpressionType(e.expressions.get(e.expressions.size()-1)));
		result = e;
	}
	
}
