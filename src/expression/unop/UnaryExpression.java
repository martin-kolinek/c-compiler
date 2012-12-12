package expression.unop;

import codegen.LabelGenerator;
import expression.Expression;
import expression.ExpressionVisitor;

public class UnaryExpression implements Expression{
	
	public UnaryExpression() {
	}
	
	public UnaryExpression(Expression exp, UnaryOperator op) {
		this.exp=exp;
		this.op=op;
	}
	
	public Expression exp;
	public UnaryOperator op;
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
