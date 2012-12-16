package codegen;

import java.util.HashMap;

import expression.Expression;

public class ExpressionValueMapping {
	public ExpressionValueMapping() {
		expRes = new HashMap<Expression, String>();
	}
	public CodeGenFactory fac;
	private HashMap<Expression, String> expRes;
	public String getExpressionResult(Expression exp) {
		String str = expRes.get(exp);
		if(str==null) {
			CodeGenExpressionVisitor expVis = fac.createExpVis();
			exp.accept(expVis);
			str = expVis.GetResultRegister();
			expRes.put(exp, str);
		}
		return str;
	}
}
