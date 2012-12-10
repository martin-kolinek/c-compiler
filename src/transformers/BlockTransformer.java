package transformers;

import java.util.ArrayList;

import statements.*;
import toplevel.EmptyInBlockVisitor;
import toplevel.InBlock;

public class BlockTransformer implements StatementVisitor {

	private BlockModifierFactory modFac;
	
	public BlockTransformer(BlockModifierFactory mod) {
		this.modFac = mod;
	}
	
	private void descend(Statement st) {
		st.accept(this);
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
		descend(dowhileStatement.body);
	}

	@Override
	public void visit(ForStatement forStatement) {
		descend(forStatement.body);
	}

	@Override
	public void visit(WhileStatement whileStatement) {
		descend(whileStatement.body);
	}

	@Override
	public void visit(SwitchStatement switchStatement) {
		for(Case c : switchStatement.cases){
			for(Statement s : c.statements) {
				descend(s);
			}
		}
		if(switchStatement.def!=null) {
			for(Statement s : switchStatement.def) {
				descend(s);
			}
		}
	}

	@Override
	public void visit(IfStatement ifStatement) {
		descend(ifStatement.ontrue);
		if(ifStatement.onfalse!=null)
			descend(ifStatement.onfalse);
	}

	@Override
	public void visit(OneexpressionStatement oneexpressionStatement) {
	}

	@Override
	public void visit(BlockStatement blockStatement) {
		BlockModifier m = modFac.createModifier();
		for(InBlock ib : blockStatement.inBlock) {
			ib.accept(new EmptyInBlockVisitor() {
				@Override
				public void visit(Statement i) {
					descend(i);
				}
			});
			ib.accept(m);
		}
		blockStatement.inBlock=new ArrayList<InBlock>(m.getModified());
		modFac.popModifierStack();
	}

}
