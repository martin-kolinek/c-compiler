package statements;

import toplevel.InBlock;
import toplevel.InBlockVisitor;

public abstract class Statement implements InBlock {
	public abstract void accept(StatementVisitor v);
	public void accept(InBlockVisitor v){
		v.visit(this);
	}
}
