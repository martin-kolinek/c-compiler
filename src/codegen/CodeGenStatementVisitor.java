package codegen;

import java.io.IOException;
import java.io.OutputStreamWriter;

import statements.BlockStatement;
import statements.BreakStatement;
import statements.ContinueStatement;
import statements.DowhileStatement;
import statements.ForStatement;
import statements.IfStatement;
import statements.OneexpressionStatement;
import statements.ReturnStatement;
import statements.StatementVisitor;
import statements.SwitchStatement;
import statements.WhileStatement;

public class CodeGenStatementVisitor implements StatementVisitor {

	private OutputStreamWriter wr;
	
	@Override
	public void visit(ReturnStatement s) {
		// TODO Auto-generated method stub
		try {
			wr.append("adsf\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void visit(BreakStatement s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ContinueStatement s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(DowhileStatement s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ForStatement s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(WhileStatement s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SwitchStatement s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IfStatement s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OneexpressionStatement s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BlockStatement s) {
		// TODO Auto-generated method stub

	}

}
