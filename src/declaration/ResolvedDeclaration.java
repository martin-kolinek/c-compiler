package declaration;

import codegen.Label;
import declaration.initializer.Initializer;
import toplevel.InBlock;
import toplevel.InBlockVisitor;
import types.Type;

public class ResolvedDeclaration implements InBlock {
	public Type type;
	public String identifier;
	public Initializer initializer;
	private Label l;
	private String zaciatok;
	private String koniec;
	@Override
	public void accept(InBlockVisitor v) {
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
