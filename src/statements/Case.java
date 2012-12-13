package statements;

import java.util.ArrayList;

import codegen.CodeGenCaseVisitor;

import expression.Expression;

public class Case {
	public Expression cond;
	public ArrayList<Statement> statements;
	
	public Case(){
		statements = new ArrayList<Statement>();
	}

	public void accept(CodeGenCaseVisitor z) {
		// TODO Auto-generated method stub
		z.visit(this);
		
	}
}
