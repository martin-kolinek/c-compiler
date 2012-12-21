package modifiers;

import declaration.ResolvedDeclaration;
import declaration.initializer.CompoundInitializer;
import expression.constant.IntConstantExpression;
import transformers.EmptyBlockModifier;
import types.ArrayType;
import types.TypeClass;

public class ArraySizeInitializerModifier extends EmptyBlockModifier {
	@Override
	public void visit(ResolvedDeclaration i) {
		if(!TypeClass.isArray(i.type)) {
			super.visit(i);
			return;
		}
		ArrayType at = (ArrayType)i.type;
		if(at.size==null && i.initializer!=null && i.initializer instanceof CompoundInitializer) {
			at.size = new IntConstantExpression(((CompoundInitializer)i.initializer).initializers.size());
		}
		super.visit(i);
	}
}
