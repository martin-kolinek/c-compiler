package transformers;

import declaration.ResolvedDeclaration;
import toplevel.FunctionDefinition;
import toplevel.FunctionParameter;

public class TypeBlockModifier extends EmptyBlockModifier {
	
	private TypeModifierFactory tmf;
	public TypeBlockModifier(TypeModifierFactory tmf) {
		this.tmf=tmf;
	}
	
	@Override
	public void visit(FunctionDefinition i) {
		i.returnType=TransformerUtil.transformType(i.returnType, tmf);
		for(FunctionParameter fp : i.parameters) {
			fp.type=TransformerUtil.transformType(fp.type, tmf);;
		}
		super.visit(i);
	}
	
	@Override
	public void visit(ResolvedDeclaration i) {
		i.type=TransformerUtil.transformType(i.type, tmf);
		super.visit(i);
	}

}
