package transformers;

import statements.BlockStatement;
import statements.BreakStatement;
import statements.ContinueStatement;
import statements.DowhileStatement;
import statements.ForStatement;
import statements.IfStatement;
import statements.OneexpressionStatement;
import statements.ReturnStatement;
import statements.Statement;
import statements.SwitchStatement;
import statements.WhileStatement;

public class EmptyStatementModifier implements StatementModifier {

	protected Statement result;
	
	@Override
	public void visit(ReturnStatement s) {
		result = s;
	}

	@Override
	public void visit(BreakStatement s) {
		result = s;
	}

	@Override
	public void visit(ContinueStatement s) {
		result = s;
	}

	@Override
	public void visit(DowhileStatement s) {
		result = s;
	}

	@Override
	public void visit(ForStatement s) {
		result = s;
	}

	@Override
	public void visit(WhileStatement s) {
		result = s;
	}

	@Override
	public void visit(SwitchStatement s) {
		result = s;
	}

	@Override
	public void visit(IfStatement s) {
		result = s;
	}

	@Override
	public void visit(OneexpressionStatement s) {
		result = s;
	}

	@Override
	public void visit(BlockStatement s) {
		result = s;
	}

	@Override
	public Statement getResult() {
		return result;
	}

}
