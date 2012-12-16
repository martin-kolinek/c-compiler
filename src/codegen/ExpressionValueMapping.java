package codegen;

import java.util.HashMap;

import expression.Expression;

public class ExpressionValueMapping {
	public ExpressionValueMapping() {
		expRes = new HashMap<Expression, String>();
	}
	
	private HashMap<Expression, String> expRes;
	public String getExpressionResult(Expression exp, CodeGenExpressionVisitor vis) {
		String str = expRes.get(exp);
		if(str==null) {
			exp.accept(vis);
			str = vis.GetResultRegister();
			expRes.put(exp, str);
		}
		return str;
	}
}
