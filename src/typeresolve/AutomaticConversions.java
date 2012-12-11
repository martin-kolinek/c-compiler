package typeresolve;

import expression.Expression;
import expression.CastExpression;
import types.ArrayType;
import types.PointerType;
import types.PrimitiveType;
import types.Type;
import types.TypeClass;

public class AutomaticConversions {
	public static Type getHigherType(Type t1, Type t2) {
		PrimitiveType pt1, pt2;
		if(TypeClass.isPointer(t1))
			pt1=PrimitiveType.LONG;
		else
			pt1=(PrimitiveType)t1;
		if(TypeClass.isPointer(t2))
			pt2=PrimitiveType.LONG;
		else
			pt2=(PrimitiveType)t2;
		int s = Math.max(pt1.size, pt2.size);
		boolean signed  = pt1.sign || pt2.sign;
		boolean floating = pt1.floating || pt2.floating;
		return PrimitiveType.getType(s, signed, floating);
	}
	
	public static Expression autoCast(Expression orig, Type to, ExpressionTypeMapping map) {
		Type from = map.getExpressionType(orig);
		assert from!=null;
		if(from==to){
			return orig;
		}
		if(TypeClass.isArithmethic(from) && TypeClass.isArithmethic(to)){
			Expression ret = new CastExpression(orig, to);
			map.setType(ret, to);
			return ret;
		}
		if(TypeClass.isPointer(from) && TypeClass.isPointer(to)){
			Expression ret = new CastExpression(orig, to);
			map.setType(ret, to);
			return ret;
		}
		if(TypeClass.isArithmethic(from) && TypeClass.isPointer(to)){
			if(from == PrimitiveType.LONG){
				Expression ret = new CastExpression(orig, to);
				map.setType(ret, to);
				return ret;
			}
			Expression inner = new CastExpression(orig, PrimitiveType.LONG);
			map.setType(inner, PrimitiveType.LONG);
			return autoCast(inner, to, map);
		}
		if(TypeClass.isPointer(from) && TypeClass.isArithmethic(to)){
			Expression inner = new CastExpression(orig, PrimitiveType.LONG);
			map.setType(inner, PrimitiveType.LONG);
			return autoCast(inner, to, map);
		}
		if(TypeClass.isArray(from) && TypeClass.isScalar(to)){
			PointerType ptr = new PointerType(((ArrayType)from).elementType);
			Expression inner = new CastExpression(orig, ptr);
			map.setType(inner, ptr);
			return autoCast(inner, to, map);
		}
		//invalid auto cast
		return null;
	}
	
	public static PointerType arrayToPtr(Type t) {
		assert TypeClass.isArray(t);
		return new PointerType(((ArrayType)t).elementType);
	}
	
	public static Expression arrayToPtr(Expression orig, ExpressionTypeMapping map) {
		Type t = map.getExpressionType(orig);
		assert t!=null;
		if(TypeClass.isPointer(t))
			return orig;
		Type ptr = arrayToPtr(t);
		Expression ret = new CastExpression(orig, ptr);
		map.setType(ret, ptr);
		return ret;
	}
}
