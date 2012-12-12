package expression;

public interface Expression {
	void accept(ExpressionVisitor v);
	void ber_l(String s);
	void zaciatok();
	void koniec();
}
