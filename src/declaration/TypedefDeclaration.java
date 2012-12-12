package declaration;

import codegen.LabelGenerator;
import toplevel.InBlock;
import toplevel.InBlockVisitor;
import types.Type;

public class TypedefDeclaration implements InBlock {

	public Type type;
	
	public String id;

	private LabelGenerator l;

	private String zaciatok;

	private String koniec;
	
	@Override
	public void accept(InBlockVisitor v) {
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
