package toplevel;

import declaration.Declaration;
import statements.BlockStatement;

public class FunctionDefinition implements InBlock {

	public BlockStatement body;
	
	public Declaration declaration;
	
	@Override
	public void accept(InBlockVisitor v) {
		v.visit(this);

	}

}
