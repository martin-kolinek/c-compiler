package expression.constant;

import codegen.Label;
import expression.Expression;
import expression.ExpressionVisitor;

public class StringConstantExpression implements Expression{
	
	public String value; //this is raw string read from source
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
