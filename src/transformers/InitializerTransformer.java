package transformers;

import declaration.initializer.CompoundInitializer;
import declaration.initializer.DesignatedInitializer;
import declaration.initializer.ExpressionInitializer;
import declaration.initializer.InitializerVisitor;

public class InitializerTransformer implements InitializerVisitor {

	private ExpressionModifierFactory fac;
	
	public InitializerTransformer(ExpressionModifierFactory fac) {
		this.fac=fac;
	}
	
	@Override
	public void visit(ExpressionInitializer expressionInitializer) {
		expressionInitializer.exp=TransformerUtil.transformExpression(expressionInitializer.exp, fac);
	}

	@Override
	public void visit(CompoundInitializer compoundInitializer) {
		for(DesignatedInitializer i:compoundInitializer.initializers) {
			if(i.designator!=null && i.designator.expr!=null) {
				i.designator.expr=TransformerUtil.transformExpression(i.designator.expr, fac);
			}
			if(i.initializer!=null)
				i.initializer.accept(this);
		}
	}

}
