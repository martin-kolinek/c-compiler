package types;

import java.util.ArrayList;
import java.util.List;

import declaration.ResolvedDeclaration;

public class StructType implements Type {

	public StructType(String tag, List<ResolvedDeclaration> members) {
		this.tag=tag;
		this.members = new ArrayList<ResolvedDeclaration>(members);
	}
	
	public final List<ResolvedDeclaration> members;
	public final String tag;
	
	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}

}
