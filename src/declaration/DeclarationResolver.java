package declaration;

import java.util.ArrayList;
import java.util.List;

import declaration.declarator.DeclaratorResolver;
import declaration.specifiers.DeclarationSpecifier;
import declaration.specifiers.SpecifierTypeExtractor;

import statements.BlockModifier;
import statements.BlockModifierFactory;
import statements.BlockTransformer;
import statements.Statement;
import toplevel.FunctionDefinition;
import toplevel.InBlock;
import toplevel.InBlockVisitor;
import types.Type;

public class DeclarationResolver implements InBlockVisitor, BlockModifier {
	
	private ArrayList<InBlock> result;
	
	@Override
	public List<InBlock> getModified() {
		return result;
	}
	
	public ArrayList<ResolvedDeclaration> resultDecls;
	
	public DeclarationResolver() {
		result = new ArrayList<InBlock>();
		resultDecls = new ArrayList<ResolvedDeclaration>();
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
				//TODO functions
			}
			if(ex.isTypedef()){
				//TODO typedef
			}
			ResolvedDeclaration decl = new ResolvedDeclaration();
			decl.type=dr.wrapType(ex.getType());
			decl.identifier=dr.getID();
			decl.initializer=d.initializer;
			result.add(decl);
			resultDecls.add(decl);
		}
	}

	@Override
	public void visit(FunctionDefinition functionDefinition) {
		//TODO
	}

	@Override
	public void visit(Type type) {
		//TODO
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
