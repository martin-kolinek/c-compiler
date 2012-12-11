package types;

public enum PrimitiveType implements Type {
	VOID(0),
	CHAR(1),
	UCHAR(2),
	SHORT(3),
	USHORT(4),
	INT(5),
	UINT(6),
	LONG(7),
	ULONG(8),
	FLOAT(9),
	DOUBLE(10);

	private PrimitiveType(int r) {
		rank=r;
	}
	
	private final int rank;
	
	public int getRank(){
		return rank;
	}
	
	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}
}
