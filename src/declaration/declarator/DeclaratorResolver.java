package declaration.declarator;

import java.util.ArrayList;

import position.GlobalPositionTracker;

import astnode.ASTNode;

import declaration.Declaration;
import declaration.DeclarationResolver;
import declaration.ResolvedDeclaration;

import exceptions.SemanticException;
import expression.Expression;

import toplevel.FunctionParameter;
import types.ArrayType;
import types.PointerType;
import types.Type;

public class DeclaratorResolver implements DeclaratorVisitor, ASTNode{

	private ASTNode pos;
	public DeclaratorResolver(ASTNode pos){
		func=false;
		this.pos=pos;
		funcParams=new ArrayList<FunctionParameter>();
		wrappers = new ArrayList<TypeWrapper>();
	}
	
	private interface TypeWrapper {
		Type wrapType(Type base);
	}
	
	private ArrayList<TypeWrapper> wrappers;
	
	public Type wrapType(Type base) {
		for(TypeWrapper w : wrappers) {
			Type n = w.wrapType(base);
			GlobalPositionTracker.pos.setPosition(n, base);
			base = n;
		}
		return base;
	}
	
	private String id;
	
	public String getID(){
		return id;
	}
	
	private boolean func;
	
	public boolean isFunction() {
		return func;
	}
	
	private boolean variad;
	public boolean isVariadic() {
		return variad;
	}
	
	public ArrayList<FunctionParameter> funcParams;
	
	@Override
	public void visit(ArrayDeclarator d) {
		if(func)
			throw new SemanticException("Function subtypes are not supported", pos);
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
			throw new SemanticException("Function subtypes are not supported", pos);
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
			throw new SemanticException("Function subtypes are not supported", pos);
		if(d.declarator!=null) {
			d.declarator.accept(this);
		}
		if(d.variadic)
			variad=true;
		for(Declaration decl : d.parameters)
		{
			DeclarationResolver res = new DeclarationResolver();
			decl.accept(res);
			if(res.resultDecls.size()==0)
				throw new SemanticException("Invalid parameter declaration",pos);
			ResolvedDeclaration rd=res.resultDecls.get(0);
			funcParams.add(new FunctionParameter(rd.identifier, rd.type));
		}
		func=true;
	}

	@Override
	public void visit(IDDeclarator d) {
		id=d.id;
	}

}
