package expression;

import astnode.ASTNode;

public interface Expression extends ASTNode{
	void accept(ExpressionVisitor v);
}
