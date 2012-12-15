package declaration;

import astnode.ASTNode;
import declaration.initializer.Initializer;
import toplevel.InBlock;
import toplevel.InBlockVisitor;
import types.Type;

public class ResolvedDeclaration implements InBlock, ASTNode {
	public Type type;
	public String identifier;
	public Initializer initializer;
	@Override
	public void accept(InBlockVisitor v) {
		v.visit(this);
	}
}
