package transformers;

import java.util.List;

import statements.Statement;
import toplevel.FunctionDefinition;
import toplevel.InBlock;
import declaration.Declaration;
import declaration.ResolvedDeclaration;
import declaration.TypedefDeclaration;

public class CompoundBlockModifier implements BlockModifier {

	private BlockModifier b1, b2;
	
	public CompoundBlockModifier(BlockModifier b1, BlockModifier b2){
		this.b1=b1;
		this.b2=b2;
	}
	
	@Override
	public void visit(Statement i) {
		i.accept(b1);
	}

	@Override
	public void visit(Declaration i) {
		i.accept(b1);
	}

	@Override
	public void visit(FunctionDefinition i) {
		i.accept(b1);
	}

	@Override
	public void visit(TypedefDeclaration i) {
		i.accept(b1);
	}

	@Override
	public void visit(ResolvedDeclaration i) {
		i.accept(b1);
	}

	@Override
	public List<InBlock> getModified() {
		for(InBlock ib:b1.getModified()) {
			ib.accept(b2);
		}
		return b2.getModified();
	}

}
