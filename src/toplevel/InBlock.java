package toplevel;

import astnode.ASTNode;

public interface InBlock extends ASTNode {
	public void accept(InBlockVisitor v);
}
