package statements;

import astnode.ASTNode;
import declaration.Declaration;
import expression.Expression;

public class ForStatement extends Statement implements ASTNode{
	
	// if (decl init; cond; after) body
	public Declaration decl;
	public Expression init;
	public Expression cond;
	public Expression after;
	public Statement body;

	public ForStatement(){
		
	}
	
	public ForStatement(Declaration decl, Expression init, Expression cond, Expression after, Statement body){
		this.decl = decl;
		this.init = init;
		this.cond = cond;
		this.after = after;
		this.body = body;
	}
	
	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);
	}

}
