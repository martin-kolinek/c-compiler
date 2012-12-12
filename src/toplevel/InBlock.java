package toplevel;

public interface InBlock {
	public void accept(InBlockVisitor v);

	void ber_l(String s);

	void zaciatok();

	void koniec();
}
