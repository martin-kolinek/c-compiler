package declaration;

import java.util.ArrayList;

import codegen.LabelGenerator;

import toplevel.InBlock;
import toplevel.InBlockVisitor;


import declaration.declarator.Declarator;
import declaration.specifiers.DeclarationSpecifier;

public class Declaration implements InBlock {

	public Declaration() {
		declSpecs=new ArrayList<DeclarationSpecifier>();
		declarators=new ArrayList<InitDeclarator>();
	}
	public Declaration(ArrayList<DeclarationSpecifier> ds) {
		declSpecs=ds;
		declarators = new ArrayList<InitDeclarator>();
	}
	public ArrayList<DeclarationSpecifier> declSpecs;
	public ArrayList<InitDeclarator> declarators;
	private LabelGenerator l;
	private String zaciatok;
	private String koniec;
	
	public void addDeclarator(Declarator decl){
		declarators.add(new InitDeclarator(decl));
	}

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
