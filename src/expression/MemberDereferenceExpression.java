package expression;

import codegen.LabelGenerator;

public class MemberDereferenceExpression implements Expression{
	
	public Expression exp;
	public String id;
	private LabelGenerator l;
	private String zaciatok;
	private String koniec;
	
	public MemberDereferenceExpression(Expression ex, String id){
		this.id=id;
		this.exp=ex;
	}

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