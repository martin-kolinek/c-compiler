package codegen;

import java.util.ArrayList;

import position.GlobalErrorCounter;
import position.GlobalPositionTracker;
import exceptions.SemanticException;
import expression.Expression;
import expression.constant.IntConstantExpression;
import statements.BlockStatement;
import statements.BreakStatement;
import statements.Case;
import statements.ContinueStatement;
import statements.DowhileStatement;
import statements.ForStatement;
import statements.IfStatement;
import statements.OneexpressionStatement;
import statements.ReturnStatement;
import statements.Statement;
import statements.StatementVisitor;
import statements.SwitchStatement;
import statements.WhileStatement;
import toplevel.InBlock;

public class CodeGenStatementVisitor implements StatementVisitor {
	
	private BlockCodeGenerator cg;
	private CodeGenStream wr;
	
	public CodeGenStatementVisitor(BlockCodeGenerator cg){
		this.cg=cg;
		wr = cg.str;
	}
	
	@Override
	public void visit(ReturnStatement s) {
		if(s.exp!=null) {
			
			wr.writeLine("ret", cg.getExpressionTypeStr(s.exp), cg.getExpressionRegister(s.exp));
		}
		else
			wr.writeLine("ret void");
				
	}

	@Override
	public void visit(BreakStatement s) {
		String BreakSkok = cg.getBreakLabel();
		if(BreakSkok == null) throw new SemanticException("Break mimo cyklu.", s);
		wr.writeLine("br", "label", BreakSkok);

	}

	@Override
	public void visit(ContinueStatement s) {
		String ContinueSkok = cg.getContLabel();
		if(ContinueSkok == null) throw new SemanticException("Continue mimo cyklu.", s);
		wr.writeLine("br", "label", ContinueSkok);

	}
	
	private void generateWhile(Expression cond, Statement body, boolean dowhile) {
		//ziskame labely
		String predCyklom = cg.getNextLabel();
		String zaCyklom = cg.getNextLabel();
		String zaPodmienkou = cg.getNextLabel();
		if(dowhile)
			wr.writeLine("br", "label", zaPodmienkou);
		//zaciatok cyklu a podmienka
		wr.writeLabel(predCyklom);
		writeCondition(cg.getExpressionRegister(cond), cg.getExpressionTypeStr(cond), zaPodmienkou, zaCyklom);
		//telo cyklu
		wr.writeLabel(zaPodmienkou);
		BlockCodeGenerator icg = new BlockCodeGenerator(cg, zaCyklom, predCyklom);
		icg.generateStatement(body);
		//skok na zaciatok
		wr.writeLine("br", "label", predCyklom);
		//label za cyklom
		wr.writeLabel(zaCyklom);
	}

	@Override
	public void visit(DowhileStatement s) {
		generateWhile(s.condition, s.body, true);
	}

	@Override
	public void visit(ForStatement s) {
		assert false; //tieto by tu uz nemali byt
	}

	private void writeCondition(String resultRegister, String resultType, String iftrue, String iffalse) {
		String condRes = cg.getNextregister();
		wr.writeAssignment(condRes, "icmp ne", resultType, "0, ",resultRegister);
		wr.writeLine("br i1", condRes, ", label", iftrue, ", label", iffalse);
	}
	
	@Override
	public void visit(WhileStatement s) {
		generateWhile(s.condition, s.body, false);
	}

	@Override
	public void visit(SwitchStatement s) {
		String zaSwitch = cg.getNextLabel();
		ArrayList<String> caseLabels = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		String defLabel = null;
		for(Case c: s.cases) {
			
			String lbl = cg.getNextLabel();
			caseLabels.add(lbl);
			if(c.cond!=null) {
				assert c.cond instanceof IntConstantExpression;
				IntConstantExpression cexp = (IntConstantExpression)c.cond;
				sb.append("i32 "+cexp.value+", label "+lbl+" ");
			}
			else {
				defLabel = lbl;
			}
		}
		if(defLabel==null) {
			defLabel=zaSwitch;
		}
		wr.writeLine("switch", cg.getExpressionTypeStr(s.expr), cg.getExpressionRegister(s.expr), ", label ", defLabel, "[" + sb.toString() + "]");
		BlockCodeGenerator icg = new BlockCodeGenerator(cg, zaSwitch, null);
		for(int i=0; i<s.cases.size(); i++) {
			wr.writeLabel(caseLabels.get(i));
			for(Statement s2 :s.cases.get(i).statements) {
				icg.generateStatement(s2);
			}
		}
		wr.writeLabel(zaSwitch);
	}

	@Override
	public void visit(IfStatement s) {
		
		//podmienka ifu
		String result = cg.getExpressionRegister(s.cond);
		String typ = cg.getExpressionTypeStr(s.cond); 
		
		//inicializacia labelov
		String Koniec = cg.getNextLabel();
		String PrvaVetva = cg.getNextLabel();
		String DruhaVetva;
		if(s.onfalse!=null)
			DruhaVetva = cg.getNextLabel();
		else
			DruhaVetva = Koniec;
		
		//vyhodnotenie podmienky
		writeCondition(result, typ, PrvaVetva, DruhaVetva);
		
		//prva vetva if-u
		wr.writeLabel(PrvaVetva);
		s.ontrue.accept(this);
		
		//skok na koniec
		wr.writeLine("br", "label", Koniec);
		
		//druha vetva if-u
		if(s.onfalse!=null) {
			wr.writeLabel(DruhaVetva);
			s.onfalse.accept(this);
		}
		//vypis label-u konca		
		wr.writeLabel(Koniec);
	}

	@Override
	public void visit(OneexpressionStatement s) {
		cg.getExpressionRegister(s.expr);
	}

	@Override
	public void visit(BlockStatement s) {
		BlockCodeGenerator icg = cg.getChild();
		CodeGenInBlockVisitor vis = new CodeGenInBlockVisitor(icg);
		for(InBlock ib:s.inBlock) {
			try {
				ib.accept(vis);
			}
			catch(SemanticException ex) {
				System.err.println(ex.getMessage(GlobalPositionTracker.pos));
				GlobalErrorCounter.errors++;
			}
			catch(NullPointerException ex){
				GlobalErrorCounter.errors++;
			}
		}
			
	}
}
