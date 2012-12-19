package types;

import java.util.HashMap;

import astnode.ASTNode;

public enum PrimitiveType implements Type, ASTNode {
	VOID(0,false, false),
	CHAR(1, true, false),
	UCHAR(1, false, false),
	SHORT(2, true, false),
	USHORT(2, false, false),
	INT(4, true, false),
	UINT(4, false, false),
	LONG(8, true, false),
	ULONG(8, false, false),
	FLOAT(4, true, true),
	DOUBLE(8, true, true);

	private PrimitiveType(int size, boolean sign, boolean floating) {
		this.size=size;
		this.sign=sign;
		this.floating=floating;
	}
	
	public final int size;
	public final boolean sign, floating;
	
	public static PrimitiveType getType(int size, boolean sign, boolean floating){
		return PrimitiveTypeCache.getInstance().getType(size, sign, floating);
	}
	
	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}
}

class PrimitiveTypeCache{
	private static PrimitiveTypeCache inst;
	public static PrimitiveTypeCache getInstance(){
		if(inst==null)
			inst=new PrimitiveTypeCache();
		return inst;
	}
	
	private HashMap<Integer, PrimitiveType> hm;
	private PrimitiveTypeCache() {
		hm=new HashMap<Integer, PrimitiveType>();
		for(PrimitiveType t:PrimitiveType.values()) {
			hm.put(getKey(t.size, t.sign, t.floating), t);
		}
	}
	
	private int getKey(int size, boolean sign, boolean floating){
		return size<<2 | (sign?2:0) |(floating?1:0);
	}
	
	public PrimitiveType getType(int size, boolean sign, boolean floating){
		PrimitiveType t = hm.get(getKey(size, sign, floating));
		assert t!=null;
		return t;
	}
}