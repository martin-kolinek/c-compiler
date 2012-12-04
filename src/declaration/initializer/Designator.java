package declaration.initializer;

import expression.Expression;

public class Designator {
	public Expression expr;
	public String id;
	public Designator(String id){
		expr=null;
		this.id=id;
	}
	public Designator(Expression expr){
		id=null;
		this.expr=expr;
	}
}
