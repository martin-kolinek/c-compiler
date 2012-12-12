package expression;

import codegen.Label;

public class IDExpression implements Expression {

	public IDExpression(String id) {
		this.id=id;
	}
	
	public String id;
	private Label l;
	private String zaciatok;
	private String koniec;
	
	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}
	
	@Override
	public void ber_l(String s) {
		// TODO Auto-generated method stub
		this.l=new Label(s);
		
	}

	@Override
	public void zaciatok() {
		// TODO Auto-generated method stub
		this.zaciatok=l.next();
		
	}

	@Override
	public void koniec() {
		// TODO Auto-generated method stub
		this.koniec=l.next();
		
	}

}
