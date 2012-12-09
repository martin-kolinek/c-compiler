package types;

import toplevel.InBlockVisitor;

public enum PrimitiveType implements Type {
	VOID,
	CHAR,
	UCHAR,
	SHORT,
	USHORT,
	INT,
	UINT,
	LONG,
	ULONG,
	FLOAT,
	DOUBLE;

	@Override
	public void accept(TypeVisitor v) {
		v.visit(this);
	}

	@Override
	public void accept(InBlockVisitor v) {
		v.visit(this);
	}
}
