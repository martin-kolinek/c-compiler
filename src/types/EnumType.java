package types;

import java.util.ArrayList;
import java.util.List;

import astnode.ASTNode;

import declaration.specifiers.Enumerator;

public class EnumType implements Type, ASTNode {

	public final String tag;
	public List<Enumerator> enumerators;
	
	public EnumType(String tag, List<Enumerator> enums) {
		this.tag=tag;
		if(enums!=null)
			enumerators = new ArrayList<Enumerator>(enums);
	}
	
	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}

}
