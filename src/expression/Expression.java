package expression;

public interface Expression {
	void accept(ExpressionVisitor v);
}
