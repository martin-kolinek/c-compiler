package transformers;

import expression.binop.BinaryExpression;
import expression.binop.BinaryOperator;
import expression.constant.IntConstantExpression;
import statements.ForStatement;
import statements.WhileStatement;

public class StatementConditionModifier extends EmptyStatementModifier{
	
	public void visit (WhileStatement ws){
		result = new WhileStatement(new BinaryExpression(ws.condition, BinaryOperator.NEQ, new IntConstantExpression(0)), ws.body);
	}
	
	public void visit (ForStatement fs){
		result = new ForStatement(fs.decl, fs.init, new BinaryExpression(fs.cond, BinaryOperator.NEQ, new IntConstantExpression(0)), fs.after, fs.body);
	}

}
