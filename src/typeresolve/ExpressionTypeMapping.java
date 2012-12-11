package typeresolve;

import java.util.HashMap;

import types.Type;
import expression.Expression;

public class ExpressionTypeMapping {
	private HashMap<Expression, Type> map;
	
	public ExpressionTypeMapping(){
		map = new HashMap<Expression, Type>();
	}
	
	public Type getExpressionType(Expression e){
		return map.get(e);
	}
	
	public void setType(Expression e, Type t) {
		map.put(e, t);
	}
}
