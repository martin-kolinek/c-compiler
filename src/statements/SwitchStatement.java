package statements;

import java.util.ArrayList;

import astnode.ASTNode;

import expression.Expression;

public class SwitchStatement extends Statement implements ASTNode {
	
	public Expression expr;
	public ArrayList<Case> cases;
	
	public SwitchStatement(){
		cases = new ArrayList<Case>();
	}

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);		
	}

}
