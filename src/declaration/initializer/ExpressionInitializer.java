package declaration.initializer;

import expression.Expression;

public class ExpressionInitializer implements Initializer {

	public ExpressionInitializer(Expression e){
		exp=e;
	}
	
	public Expression exp; 
	
	@Override
	public void accept(InitializerVisitor v) {
		v.visit(this);
	}

}
