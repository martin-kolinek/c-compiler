package typeresolve;

import exceptions.SemanticException;
import expression.*;
import expression.binop.BinaryExpression;
import expression.constant.*;
import expression.unop.UnaryExpression;
import symbols.SymbolTable;
import transformers.ExpressionModifier;
import types.Type;

public class TypeResolverExpressionModifier implements ExpressionModifier {

	private SymbolTable<Type> symb;
	private ExpressionTypeMapping mapping;
	public TypeResolverExpressionModifier(SymbolTable<Type> symb, ExpressionTypeMapping mp) {
		this.symb=symb;
		mapping=mp;
	}
	
	@Override
	public void visit(BinaryExpression e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(UnaryExpression e) {
		Type t = mapping.getExpressionType(e);
		assert t!=null;
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
