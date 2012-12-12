package expression;

import codegen.Label;

public class TernaryExpression implements Expression {

	public Expression condition;
	public Expression ontrue;
	public Expression onfalse;
	private Label l;
	private String zaciatok;
	private String koniec;
	
	public TernaryExpression(Expression cond, Expression tr, Expression fa){
		condition=cond;
		ontrue=tr;
		onfalse=fa;
	}
	
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
