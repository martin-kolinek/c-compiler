package modifiers;

import toplevel.FunctionDefinition;
import toplevel.FunctionParameter;
import transformers.EmptyBlockModifier;
import typeresolve.AutomaticConversions;
import types.PointerType;
import types.TypeClass;

public class FunctionParameterModifier extends EmptyBlockModifier {
	@Override
	public void visit(FunctionDefinition i) {
		for(FunctionParameter p: i.parameters) {
			if(TypeClass.isStruct(p.type)) {
				p.type = new PointerType(p.type);
			}
			if(TypeClass.isArray(p.type)){
				p.type = AutomaticConversions.arrayToPtr(p.type);
			}
		}
		super.visit(i);
	}
}
