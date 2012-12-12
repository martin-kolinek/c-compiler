package expression;

import java.util.ArrayList;

import codegen.Label;

public class FunctionCallExpression implements Expression {

	public FunctionCallExpression(String name) {
		args=new ArrayList<Expression>();
		this.name=name;
	}
	
	public String name;
	public ArrayList<Expression> args;
	private Label l;
	private String zaciatok;
	private String koniec;
	
	public void addExp(Expression exp){
		args.add(exp);
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