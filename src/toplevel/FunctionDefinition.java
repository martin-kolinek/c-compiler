package toplevel;

import java.util.ArrayList;

import codegen.Label;

import declaration.Declaration;
import statements.Statement;
import types.Type;

public class FunctionDefinition implements InBlock {

	public Statement body;
	
	public Declaration declaration;
	
	public Type returnType;
	
	public ArrayList<FunctionParameter> parameters;
	
	public String name;

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
