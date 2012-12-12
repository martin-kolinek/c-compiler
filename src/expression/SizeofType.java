package expression;

import codegen.LabelGenerator;
import declaration.Declaration;
import types.Type;

public class SizeofType implements Expression{
	
	public Declaration typedecl;
	
	public Type type;

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