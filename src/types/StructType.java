package types;

import java.util.List;
import declaration.Declaration;

public class StructType extends Type {

	public List<Declaration> members;
	
	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}

}
