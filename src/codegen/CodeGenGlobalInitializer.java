package codegen;

import toplevel.EmptyInBlockVisitor;
import typeresolve.ExpressionTypeMapping;
import declaration.ResolvedDeclaration;

public class CodeGenGlobalInitializer extends EmptyInBlockVisitor {

	BlockCodeGenerator cg;
	
	@Override
	public void visit(ResolvedDeclaration i) {
		String Typ=cg.getTypeString(i.type);
		cg.
	}

}
