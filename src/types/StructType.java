package types;

import java.util.ArrayList;
import java.util.List;

import astnode.ASTNode;

import declaration.ResolvedDeclaration;

public class StructType implements Type, ASTNode {

	public StructType(String tag, List<ResolvedDeclaration> members) {
		this.tag=tag;
		if(members!=null)
			this.members = new ArrayList<ResolvedDeclaration>(members);
	}
	
	public ResolvedDeclaration getMember(String name) {
		for(ResolvedDeclaration m:members) {
			if(m.identifier.equals(name)) {
				return m;
			}
		}
		return null;
	}
	
	public List<ResolvedDeclaration> members;
	public final String tag;
	
	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}

}
