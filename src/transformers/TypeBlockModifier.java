package transformers;

import declaration.ResolvedDeclaration;
import toplevel.FunctionDefinition;
import toplevel.FunctionParameter;
import types.Type;

public class TypeBlockModifier extends EmptyBlockModifier {
	
	private TypeModifierFactory tmf;
	public TypeBlockModifier(TypeModifierFactory tmf) {
		this.tmf=tmf;
	}
	
	private Type transform(Type t){
		TypeTransformer tt = new TypeTransformer(tmf);
		t.accept(tt);
		TypeModifier mod = tmf.create();
		t.accept(mod);
		return mod.getResult();
	}
	
	@Override
	public void visit(FunctionDefinition i) {
		i.returnType=transform(i.returnType);
		for(FunctionParameter fp : i.parameters) {
			fp.type=transform(fp.type);
		}
		super.visit(i);
	}
	
	@Override
	public void visit(ResolvedDeclaration i) {
		i.type=transform(i.type);
		super.visit(i);
	}
}
