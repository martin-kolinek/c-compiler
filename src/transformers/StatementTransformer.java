package transformers;

import java.util.ArrayList;

import declaration.Declaration;
import declaration.ResolvedDeclaration;
import declaration.TypedefDeclaration;
import statements.*;
import toplevel.FunctionDefinition;
import toplevel.InBlock;
import toplevel.InBlockVisitor;

public class StatementTransformer implements StatementVisitor {

	StatementModifierFactory modFac;
	
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
		for(int i=0; i<blockStatement.inBlock.size(); i++) {
			InBlock ib = blockStatement.inBlock.get(i);
			final int x = i;
			final ArrayList<InBlock> ibs = blockStatement.inBlock;
			ib.accept(new InBlockVisitor() {
				@Override
				public void visit(ResolvedDeclaration resolvedDeclaration) {
				}
				@Override
				public void visit(TypedefDeclaration typedefDeclaration) {
				}
				@Override
				public void visit(FunctionDefinition functionDefinition) {
				}
				@Override
				public void visit(Declaration declaration) {
				}
				@Override
				public void visit(Statement statement) {
					ibs.set(x, descend(statement));
				}
			});
		}
	}
	
}
