package types;

import expression.Expression;
import expression.IDExpression;
import symbols.SymbolTable;
import transformers.EmptyExpressionModifier;

public class EnumRemoverExpressionMod extends EmptyExpressionModifier {
	
	SymbolTable<Expression> enumerators;
	
	public EnumRemoverExpressionMod(SymbolTable<Expression> enumerators){
		this.enumerators = enumerators;
	}
	
	@Override
	public void visit(IDExpression e) {
		Expression e2 = enumerators.get(e.id);
		if(e2!=null)
			result = (e2);
		else
			super.visit(e);
	}
}
