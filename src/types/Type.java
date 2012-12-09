package types;

import toplevel.InBlock;

public interface Type extends InBlock {
	void accept(TypeVisitor v);
	
}
