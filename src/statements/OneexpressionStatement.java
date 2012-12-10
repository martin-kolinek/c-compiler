package statements;

import expression.Expression;

public class OneexpressionStatement extends Statement{

	public OneexpressionStatement() {
		
	}
	
	public OneexpressionStatement(Expression e){
		exp=e;
	}
	
	public Expression exp;

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
