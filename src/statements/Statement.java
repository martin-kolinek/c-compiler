package statements;

import codegen.LabelGenerator;
import toplevel.InBlock;
import toplevel.InBlockVisitor;

public abstract class Statement implements InBlock {
	public String zaciatok;//1 - zaciatok bloku, 
	public String koniec;//2 - koniec bloku
	public LabelGenerator l;
	
	public abstract void accept(StatementVisitor v);
	
	public void accept(InBlockVisitor v){
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
