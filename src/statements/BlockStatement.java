package statements;

import java.util.ArrayList;

public class BlockStatement implements Statement {
	
	public ArrayList<Object> in_block;

	@Override
	public void accept(StatementVisitor v) {
		v.visit(this);		
	}

}
