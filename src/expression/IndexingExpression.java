package expression;

import codegen.Label;

public class IndexingExpression implements Expression {

	public IndexingExpression(Expression target, Expression index){
		this.target=target;
		this.index=index;
	}
	
	public Expression index;
	public Expression target;
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
