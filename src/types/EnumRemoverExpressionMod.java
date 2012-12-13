package types;

import expression.IDExpression;
import expression.constant.IntConstantExpression;
import symbols.SymbolTable;
import transformers.EmptyExpressionModifier;

public class EnumRemoverExpressionMod extends EmptyExpressionModifier {
	
	SymbolTable<Integer> ids;
	
	public EnumRemoverExpressionMod(SymbolTable<Integer> vals){
		ids=vals;
	}
	
	@Override
	public void visit(IDExpression e) {
		Integer i = ids.get(e.id);
		if(i!=null)
			super.visit(new IntConstantExpression(i));
		else
			super.visit(e);
	}
}
