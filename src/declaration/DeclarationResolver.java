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
import transformers.BlockModifierFactory;
import transformers.BlockTransformer;

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
		BlockTransformer trans = new BlockTransformer(new BlockModifierFactory() {
			
			@Override
			public BlockModifier createModifier() {
				return new DeclarationResolver();
			}
		});
		statement.accept(trans);
	}

	@Override
	public void visit(Declaration declaration) {
		SpecifierTypeExtractor ex = new SpecifierTypeExtractor();
		for(DeclarationSpecifier sp : declaration.declSpecs) {
			sp.accept(ex);
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
