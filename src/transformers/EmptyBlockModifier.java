package transformers;

import java.util.ArrayList;
import java.util.List;

import statements.Statement;
import toplevel.FunctionDefinition;
import toplevel.InBlock;
import declaration.Declaration;
import declaration.ResolvedDeclaration;
import declaration.TypedefDeclaration;

public class EmptyBlockModifier implements BlockModifier {

	protected ArrayList<InBlock> result;
	
	public EmptyBlockModifier() {
		result = new ArrayList<InBlock>();
	}
	
	@Override
	public void visit(Statement i) {
		result.add(i);
	}

	@Override
	public void visit(Declaration i) {
		result.add(i);
	}

	@Override
	public void visit(FunctionDefinition i) {
		result.add(i);
	}

	@Override
	public void visit(TypedefDeclaration i) {
		result.add(i);
	}

	@Override
	public void visit(ResolvedDeclaration i) {
		result.add(i);
	}

	@Override
	public List<InBlock> getModified() {
		return result;
	}

}
