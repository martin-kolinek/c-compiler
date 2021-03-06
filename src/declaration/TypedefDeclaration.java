package declaration;

import astnode.ASTNode;
import toplevel.InBlock;
import toplevel.InBlockVisitor;
import types.Type;

public class TypedefDeclaration implements InBlock, ASTNode {

	public Type type;
	
	public String id;
	
	@Override
	public void accept(InBlockVisitor v) {
		v.visit(this);
	}

}
