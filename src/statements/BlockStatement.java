package statements;

import java.util.ArrayList;

import toplevel.InBlock;


public class BlockStatement extends Statement {
	
	public BlockStatement(){
		inBlock=new ArrayList<InBlock>();
	}
	
	public ArrayList<InBlock> inBlock;

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);		
	}

}
