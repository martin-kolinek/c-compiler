package expression.binop;

import codegen.Label;
import expression.Expression;
import expression.ExpressionVisitor;

public class BinaryExpression implements Expression {
	
	public BinaryExpression(Expression e1, BinaryOperator op, Expression e2) {
		left=e1;
		right=e2;
		operator=op;
	}
	
	public String zaciatok;//1 - zaciatok bloku, 
	public String koniec;//2 - koniec bloku
	public Label l;
	
	public BinaryOperator operator;
	public Expression left;
	public Expression right;
	
	@Override
	public void accept(ExpressionVisitor v){
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
