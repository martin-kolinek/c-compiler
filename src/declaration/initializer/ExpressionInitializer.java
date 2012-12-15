package declaration.initializer;

import astnode.ASTNode;
import expression.Expression;

public class ExpressionInitializer implements Initializer, ASTNode {

	public ExpressionInitializer(Expression e){
		exp=e;
	}
	
	public Expression exp; 
	
	@Override
	public void accept(InitializerVisitor v) {
		v.visit(this);
	}

}
