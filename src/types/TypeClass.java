package types;

public class TypeClass {
	public static boolean isArithmethic(Type t) {
		return t instanceof PrimitiveType && t!=PrimitiveType.VOID;
	}
	
	public static boolean isPointer(Type t){
		return t instanceof PointerType;
	}
	
	public static boolean isPointerOrArray(Type t) {
		return t instanceof PointerType || t instanceof ArrayType;
	}
	
	public static boolean isScalar(Type t) {
		return isPointerOrArray(t) || isArithmethic(t);
	}
	
	public static boolean isStruct(Type t) {
		return t instanceof StructType;
	}
	
	public static boolean isArray(Type t) {
		return t instanceof ArrayType;
	}
	
	public static boolean isInteger(Type t) {
		if(!isArithmethic(t))
			return false;
		return t==PrimitiveType.CHAR || t==PrimitiveType.INT || t==PrimitiveType.LONG || t==PrimitiveType.SHORT || t==PrimitiveType.UCHAR || t==PrimitiveType.UINT || t==PrimitiveType.ULONG || t==PrimitiveType.USHORT;
	}
	
	
}
