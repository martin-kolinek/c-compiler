package types;

import codegen.IRValue;
import codegen.IntConstant;

public class IntType implements Type {

	@Override
	public IRValue getSize() {
		return new IntConstant(4);
	}

}
