package types;

import codegen.IRValue;

public interface Type {
	IRValue getSize();
	String getStringRepresentation();
}
