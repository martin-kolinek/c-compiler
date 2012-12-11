package declaration;

import exceptions.SemanticException;
import expression.CastExpression;
import expression.SizeofType;
import transformers.EmptyExpressionModifier;
import types.Type;

public class ExpressionDeclarationResolver extends EmptyExpressionModifier {
	
	private Type extractType(Declaration d) {
		DeclarationResolver res = new DeclarationResolver();
		d.accept(res);
		if(res.resultDecls.size()==0 || res.resultDecls.get(0).identifier!=null)
			throw new SemanticException("Invalid type argument to sizeof");
		return res.resultDecls.get(0).type;
	}
	
	@Override
	public void visit(SizeofType e) {
		e.type=extractType(e.typedecl);
		super.visit(e);
	}

	
	
	@Override
	public void visit(CastExpression e) {
		e.type=extractType(e.typedecl);
		super.visit(e);
	}
}
