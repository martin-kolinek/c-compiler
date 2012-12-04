package statements;

import java.util.ArrayList;

import expression.Expression;

public class Case {
	public Expression cond;
	public ArrayList<Statement> statements;
	
	public Case(){
		statements = new ArrayList<Statement>();
	}
}
