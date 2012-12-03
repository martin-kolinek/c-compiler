package declaration.declarator;

import java.util.ArrayList;

import declaration.specifiers.TypeQualifier;

import expression.Expression;

public class ArrayDeclarator implements Declarator {
	
	ArrayDeclarator(Declarator d,boolean ast, boolean st) {
		stat=st;
		asterisk=ast;
		declarator=d;
		qualifiers=new ArrayList<TypeQualifier>();
	}
	
	public Declarator declarator;
	
	public boolean asterisk;
	
	public boolean stat;
	
	public Expression expression;
	
	public ArrayList<TypeQualifier> qualifiers;

	@Override
	public void accept(DeclaratorVisitor v) {
		v.visit(this);
	}

}
