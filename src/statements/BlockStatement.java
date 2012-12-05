package statements;

import java.util.ArrayList;

import toplevel.InBlock;


public class BlockStatement extends Statement {
	
	public ArrayList<InBlock> inBlock;

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);		
	}

}
