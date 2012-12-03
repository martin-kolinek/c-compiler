package declaration.declarator;

public interface DeclaratorVisitor {

	void visit(ArrayDeclarator arrayDeclaratorSuffix);

	void visit(PointerDeclarator pointerDeclarator);

	void visit(FunctionDeclarator functionDeclaratorSuffix);

	void visit(IDDeclarator idDeclarator);

}
