package types;

import java.util.ArrayList;
import java.util.List;

import declaration.specifiers.Enumerator;

public class EnumType implements Type {

	public final String tag;
	public final List<Enumerator> enumerators;
	
	public EnumType(String tag, List<Enumerator> enums) {
		this.tag=tag;
		enumerators = new ArrayList<Enumerator>(enums);
	}
	
	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}

}
