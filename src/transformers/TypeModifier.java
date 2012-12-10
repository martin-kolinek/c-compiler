package transformers;

import types.Type;
import types.TypeVisitor;

public interface TypeModifier extends TypeVisitor {
	Type getResult();
}
