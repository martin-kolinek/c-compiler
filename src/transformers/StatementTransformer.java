package transformers;

import java.util.ArrayList;

import statements.*;
import toplevel.InBlock;

public class StatementTransformer implements StatementVisitor {

	public StatementTransformer(StatementModifierFactory mod) {
		modFac=mod;
	}
	
	private StatementModifierFactory modFac;
	
	private Statement descend(Statement s) {
		s.accept(this);
		StatementModifier mod = modFac.create();
		s.accept(mod);
		return mod.getResult();
	}
	
	@Override
	public void visit(ReturnStatement returnStatement) {
	}

	@Override
	public void visit(BreakStatement breakStatement) {	
	}

	@Override
	public void visit(ContinueStatement continueStatement) {
	}

	@Override
	public void visit(DowhileStatement dowhileStatement) {
		dowhileStatement.body=descend(dowhileStatement.body);
	}

	@Override
	public void visit(ForStatement forStatement) {
		forStatement.body=descend(forStatement.body);
	}

	@Override
	public void visit(WhileStatement whileStatement) {
		whileStatement.body=descend(whileStatement.body);
	}

	@Override
	public void visit(SwitchStatement switchStatement) {
		for(Case c : switchStatement.cases) {
			for(int i=0; i<c.statements.size(); i++) {
				c.statements.set(i, descend(c.statements.get(i)));
			}
		}
		for(int i=0; i<switchStatement.def.size(); i++) {
			switchStatement.def.set(i, descend(switchStatement.def.get(i)));
		}
	}

	@Override
	public void visit(IfStatement ifStatement) {
		ifStatement.ontrue=descend(ifStatement.ontrue);
		if(ifStatement.onfalse!=null){
			ifStatement.onfalse=descend(ifStatement.onfalse);
		}
	}

	@Override
	public void visit(OneexpressionStatement oneexpressionStatement) {
	}

	@Override
	public void visit(BlockStatement blockStatement) {
		BlockModifier bm = new EmptyBlockModifier() {
			@Override
			public void visit(Statement s) {
				result.add(descend(s));
			}
		};
		for(InBlock ib : blockStatement.inBlock) {
			ib.accept(bm);
		}
		blockStatement.inBlock=new ArrayList<InBlock>(bm.getModified());
	}
	
}
