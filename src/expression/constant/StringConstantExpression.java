package expression.constant;

import java.util.HashMap;
import astnode.ASTNode;
import expression.Expression;
import expression.ExpressionVisitor;

public class StringConstantExpression implements Expression, ASTNode{
	
	public byte[] value;
	
	public StringConstantExpression(String input) {
		input = input.substring(1, input.length()-1);
		HashMap<String, String> replacements = new HashMap<String, String>();
		replacements.put("\\n", "\n");
		replacements.put("\\t", "\t");
		replacements.put("\\\\", "\\");
		for(String k:replacements.keySet()) {
			input = input.replace(k, replacements.get(k));
		}
		value = input.getBytes();
	}

	@Override
	public void accept(ExpressionVisitor v) {
		v.visit(this);
	}

}
