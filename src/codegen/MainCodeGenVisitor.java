package codegen;

import declaration.Declaration;
import declaration.ResolvedDeclaration;
import declaration.TypedefDeclaration;
import statements.BlockStatement;
import statements.Statement;
import toplevel.FunctionDefinition;
import toplevel.InBlockVisitor;

public class MainCodeGenVisitor implements InBlockVisitor {
	public BlockCodeGenerator cg;

	@Override
	public void visit(Statement i) {
		// TODO Auto-generated method stub
		CodeGenStatementVisitor sv = new CodeGenStatementVisitor(cg);
		i.accept(sv);
		if(sv.isBlock()){
			BlockStatement b=sv.getBlock();
			BlockCodeGenerator cgch=cg.getChild();
		}

	}

	@Override
	public void visit(Declaration i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(FunctionDefinition i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TypedefDeclaration i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ResolvedDeclaration i) {
		// TODO Auto-generated method stub

	}

}
