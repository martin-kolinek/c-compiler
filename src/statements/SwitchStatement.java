package statements;

import java.util.ArrayList;

import astnode.ASTNode;

import expression.Expression;

public class SwitchStatement extends Statement implements ASTNode {
	
	public Expression expr;
	public ArrayList<Case> cases;
	public ArrayList<Statement> def;
	
	public SwitchStatement(){
		cases = new ArrayList<Case>();
		def = new ArrayList<Statement>();
	}

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);		
	}

}
