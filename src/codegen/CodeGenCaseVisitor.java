package codegen;

import statements.Case;
import statements.CaseVisitor;

public class CodeGenCaseVisitor implements CaseVisitor {
	
	private RegisterGenerator r;

	public CodeGenCaseVisitor(RegisterGenerator r) {
		// TODO Auto-generated constructor stub
		this.r=r;
	}

	@Override
	public void visit(Case c) {
		// TODO Auto-generated method stub

	}

}
