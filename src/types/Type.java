package types;

import astnode.ASTNode;

public interface Type extends ASTNode{
	void accept(TypeVisitor v);
	
}
