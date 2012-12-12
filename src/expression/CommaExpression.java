package expression;

import java.util.ArrayList;

import codegen.LabelGenerator;

public class CommaExpression implements Expression {

	public CommaExpression(Expression e){
		expressions=new ArrayList<Expression>();
		expressions.add(e);
	}
	
	public ArrayList<Expression> expressions;
	private LabelGenerator l;
	private String zaciatok;
	private String koniec;
	
	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}
	
	@Override
	public void ber_l(String s) {
		// TODO Auto-generated method stub
		this.l=new LabelGenerator(s);
		
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
