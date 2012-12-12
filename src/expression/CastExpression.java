package expression;

import codegen.Label;
import types.Type;
import declaration.Declaration;

public class CastExpression implements Expression{
	
	public CastExpression(){
	}
	
	public CastExpression(Expression e, Type t){
		exp=e;
		type=t;
	}
	
	public Declaration typedecl;
	public Expression exp;
	public Type type;

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

	
	public String zaciatok;//1 - zaciatok bloku, 
	public String koniec;//2 - koniec bloku
	public Label l;

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
