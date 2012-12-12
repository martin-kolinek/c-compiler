package declaration;

import java.util.ArrayList;
import java.util.List;

import declaration.declarator.DeclaratorResolver;
import declaration.specifiers.DeclarationSpecifier;
import declaration.specifiers.SpecifierTypeExtractor;
import exceptions.SemanticException;

import statements.Statement;
import toplevel.FunctionDefinition;
import toplevel.InBlock;
import transformers.BlockModifier;
import transformers.ExpressionModifier;
import transformers.ExpressionModifierFactory;
import transformers.ExpressionStatementModifier;
import transformers.StatementModifier;
import transformers.StatementModifierFactory;
import transformers.StatementTransformer;

public class DeclarationResolver implements BlockModifier {
	
	private ArrayList<InBlock> result;
	
	@Override
	public List<InBlock> getModified() {
		return result;
	}
	
	public ArrayList<ResolvedDeclaration> resultDecls;
	private ArrayList<FunctionDefinition> resultFuncs;
	
	public DeclarationResolver() {
		result = new ArrayList<InBlock>();
		resultDecls = new ArrayList<ResolvedDeclaration>();
		resultFuncs=new ArrayList<FunctionDefinition>();
	}

	@Override
	public void visit(Statement statement) {
		StatementModifierFactory stmodfac = new StatementModifierFactory() {
			@Override
			public StatementModifier create() {
				return new ExpressionStatementModifier(new ExpressionModifierFactory() {
					@Override
					public ExpressionModifier create() {
						return new ExpressionDeclarationResolver();
					}
				});
			}
		};
		StatementTransformer sttrans = new StatementTransformer(stmodfac);
		statement.accept(sttrans);
		StatementModifier stmod =stmodfac.create(); 
		statement.accept(stmod);
		statement = stmod.getResult();
		
		result.add(statement);
	}

	@Override
	public void visit(Declaration declaration) {
		SpecifierTypeExtractor ex = new SpecifierTypeExtractor();
		for(DeclarationSpecifier sp : declaration.declSpecs) {
			sp.accept(ex);
		}
		if(declaration.declarators.size()==0) {
			if(!ex.isTypedef()){
				ResolvedDeclaration rd = new ResolvedDeclaration();
				rd.type=ex.getType();
				result.add(rd);
				resultDecls.add(rd);
			}
		}
		for(InitDeclarator d : declaration.declarators) {
			DeclaratorResolver dr = new DeclaratorResolver();
			d.declarator.accept(dr);
			if(dr.isFunction()) {
				FunctionDefinition fun = new FunctionDefinition();
				fun.returnType=ex.getType();
				fun.parameters=dr.funcParams;
				fun.name=dr.getID();
				if(d.initializer!=null)
					throw new SemanticException("Initizer for function");
				result.add(fun);
				resultFuncs.add(fun);
			}
			else if(ex.isTypedef()){
				TypedefDeclaration td = new TypedefDeclaration();
				td.id=dr.getID();
				td.type=dr.wrapType(ex.getType());
				if(d.initializer!=null)
					throw new SemanticException("Initializer for typedef");
				result.add(td);
			}
			else {
				ResolvedDeclaration decl = new ResolvedDeclaration();
				decl.type=dr.wrapType(ex.getType());
				decl.identifier=dr.getID();
				decl.initializer=d.initializer;
				result.add(decl);
				resultDecls.add(decl);
			}
		}
	}

	@Override
	public void visit(FunctionDefinition functionDefinition) {
		DeclarationResolver res =new DeclarationResolver();
		functionDefinition.declaration.accept(res);
		if(res.resultFuncs.size()==0)
			throw new SemanticException("Declaration if function definition wrong");
		FunctionDefinition def =res.resultFuncs.get(0);
		def.body=functionDefinition.body;
		result.add(def);
		//resultFuncs does not need to be modified as it is only used here and the DeclarationResolver is accepted by Declaration
	}

	@Override
	public void visit(TypedefDeclaration typedefDeclaration) {
		assert false;
	}

	@Override
	public void visit(ResolvedDeclaration resolvedDeclaration) {
		assert false;
	}

}
