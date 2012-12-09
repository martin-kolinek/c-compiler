package declaration.declarator;

import java.util.ArrayList;

import exceptions.SemanticException;
import expression.Expression;

import types.ArrayType;
import types.PointerType;
import types.Type;

public class DeclaratorResolver implements DeclaratorVisitor {

	public DeclaratorResolver(){
		func=false;
	}
	
	private interface TypeWrapper {
		Type wrapType(Type base);
	}
	
	private ArrayList<TypeWrapper> wrappers;
	
	public Type wrapType(Type base) {
		for(TypeWrapper w : wrappers) {
			base = w.wrapType(base);
		}
		return base;
	}
	
	private String id;
	
	public String getID(){
		return id;
	}
	
	private boolean func;
	
	@Override
	public void visit(ArrayDeclarator d) {
		if(func)
			throw new SemanticException("Function subtypes are not supported");
		if(d.declarator!=null) {
			d.declarator.accept(this);
		}
		final Expression e=d.expression; 
		wrappers.add(new TypeWrapper() {

			@Override
			public Type wrapType(Type base) {
				return new ArrayType(base, e);
			}
			
		});
	}

	@Override
	public void visit(PointerDeclarator d) {
		if(func)
			throw new SemanticException("Function subtypes are not supported");
		if(d.declarator!=null) {
			d.declarator.accept(this);
		}
		wrappers.add(new TypeWrapper() {
			@Override
			public Type wrapType(Type base) {
				return new PointerType(base);
			}			
		});
	}

	@Override
	public void visit(FunctionDeclarator d) {
		if(func)
			throw new SemanticException("Function subtypes are not supported");
		if(d.declarator!=null) {
			d.declarator.accept(this);
		}
		func=true;
	}

	@Override
	public void visit(IDDeclarator d) {
		id=d.id;
	}

}
