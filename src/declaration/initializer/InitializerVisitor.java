package declaration.initializer;

public interface InitializerVisitor {

	void visit(ExpressionInitializer expressionInitializer);

	void visit(CompoundInitializer compoundInitializer);

}
