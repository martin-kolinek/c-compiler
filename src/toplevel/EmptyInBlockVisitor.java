package toplevel;

import statements.Statement;
import declaration.Declaration;
import declaration.ResolvedDeclaration;
import declaration.TypedefDeclaration;

public class EmptyInBlockVisitor implements InBlockVisitor {

	@Override
	public void visit(Statement i) {
	}

	@Override
	public void visit(Declaration i) {
	}

	@Override
	public void visit(FunctionDefinition i) {
	}

	@Override
	public void visit(TypedefDeclaration i) {
	}

	@Override
	public void visit(ResolvedDeclaration i) {
	}

}
