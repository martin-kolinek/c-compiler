package declaration.specifiers;

import expression.Expression;

public class Enumerator {
	public Enumerator(String n, Expression e){
		expression=e;
		name=n;
	}
	public final String name;
	public final Expression expression;
}
