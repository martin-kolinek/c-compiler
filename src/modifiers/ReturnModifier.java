package modifiers;

import statements.ReturnStatement;
import toplevel.FunctionDefinition;
import transformers.EmptyStatementModifier;
import typeresolve.AutomaticConversions;
import typeresolve.ExpressionTypeMapping;

public class ReturnModifier extends EmptyStatementModifier {
	FunctionDefinition func;
	ExpressionTypeMapping map;
	
	public ReturnModifier(FunctionDefinition func, ExpressionTypeMapping map) {
		this.func=func;
		this.map=map;
	}
	
	@Override
	public void visit(ReturnStatement s) {
		if(s.exp!=null)
			s.exp=AutomaticConversions.autoCast(s.exp, func.returnType, map);
		super.visit(s);
	}
}
