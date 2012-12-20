package codegen;

import java.util.ArrayList;
import exceptions.SemanticException;
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

public class CodeGenStatementVisitor implements StatementVisitor {
	
	private BlockCodeGenerator cg;
	private String BreakSkok;
	private String ContinueSkok;
	private CodeGenStream wr;
	private BlockStatement s =null;
	
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
			wr.writeLine("ret");
				
	}

	@Override
	public void visit(BreakStatement s) {
		if(BreakSkok == null) throw new SemanticException("Break mimo cyklu.");
		wr.writeLine("br", "label", BreakSkok);

	}

	@Override
	public void visit(ContinueStatement s) {
		if(ContinueSkok == null) throw new SemanticException("Continue mimo cyklu.");
		wr.writeLine("br", "label", ContinueSkok);

	}

	@Override
	public void visit(DowhileStatement s) {
		assert false; //tieto by tu uz nemali byt

	}

	@Override
	public void visit(ForStatement s) {
		assert false; //tieto by tu uz nemali byt
	}

	private void writeCondition(String resultRegister, String resultType, String iftrue, String iffalse) {
		String condRes = cg.getNextregister();
		wr.writeAssignment(condRes, "icmp ne", resultType, "0, ",resultRegister);
		wr.writeLine("br i1", condRes, ", label", iftrue, "label", iffalse);
	}
	
	@Override
	public void visit(WhileStatement s) {
		//ziskame labely
		String predCyklom = cg.getNextLabel();
		String zaCyklom = cg.getNextLabel();
		String zaPodmienkou = cg.getNextLabel();
		//zaciatok cyklu a podmienka
		wr.writeLabel(predCyklom);
		writeCondition(cg.getExpressionRegister(s.condition), cg.getExpressionTypeStr(s.condition), zaPodmienkou, zaCyklom);
		//telo cyklu
		wr.writeLabel(zaPodmienkou);
		CodeGenStatementVisitor childVisitor = new CodeGenStatementVisitor(cg);
		childVisitor.BreakSkok=zaCyklom;
		childVisitor.ContinueSkok=predCyklom;
		s.body.accept(childVisitor);
		//skok na zaciatok
		wr.writeLine("br", "label", predCyklom);
		//label za cyklom
		wr.writeLabel(zaCyklom);
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
		CodeGenStatementVisitor childVis = new CodeGenStatementVisitor(cg);
		childVis.BreakSkok=zaSwitch;
		for(int i=0; i<s.cases.size(); i++) {
			wr.writeLabel(caseLabels.get(i));
			for(Statement s2 :s.cases.get(i).statements) {
				s2.accept(childVis);
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
		//ok riesi MainCodeGenvisitor
		this.s =s;
	}
	boolean isBlock(){
		return s==null;
	}

	public BlockStatement getBlock() {
		return s;
	}

}
