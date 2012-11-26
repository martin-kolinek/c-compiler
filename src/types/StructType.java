package types;

import java.util.Collection;
import java.util.HashMap;

public class StructType implements Type {

	private HashMap<String, Type> members;
	
	public Collection<String> getMembers(){
		return members.keySet();
	}
	
	public Type getMemberType(String member){
		return members.get(member);
	}
	
	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}

}
