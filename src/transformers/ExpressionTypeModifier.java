package transformers;

import types.ArrayType;


public class ExpressionTypeModifier extends EmptyTypeModifier {

	private ExpressionModifierFactory fac;
	public ExpressionTypeModifier(ExpressionModifierFactory fac) {
		this.fac=fac;
	}
	
	@Override
	public void visit(ArrayType t) {
		t.size=TransformerUtil.transformExpression(t.size, fac);
		super.visit(t);
	}

}
