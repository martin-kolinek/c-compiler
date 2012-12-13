package codegen;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import statements.Case;
import statements.CaseVisitor;
import statements.Statement;

public class CodeGenCaseVisitor implements CaseVisitor {
	
	private void pis(OutputStreamWriter o,String s){
		try {
			o.append(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<CaseLine> moje;
	
	private RegisterGenerator r;
	public String Koniec;
	public String dalsi;
	public String zaciatok;

	private OutputStreamWriter wr;

	private LabelGenerator l;

	private VisitPack pack;

	public CodeGenCaseVisitor(VisitPack pack) {
		this.pack=pack;
		this.l=this.pack.l;
		this.r=this.pack.r;
		this.wr=this.pack.wr;
	}

	@Override
	public void visit(Case c) {
		
		//vypocitanie expression
		CodeGenExpressionVisitor e = new CodeGenExpressionVisitor(pack);
		c.cond.accept(e);
		String result = e.GetResultRegister();
		
		//zaciatocny label statementov
		
		String v = zaciatok + ":\n";
		pis(wr,v);
		
		VisitPack p = new VisitPack(wr,l,r,pack.table);
		
		CodeGenStatementVisitor q = new CodeGenStatementVisitor(p);
		q.BreakSkok=Koniec;
		
		for(Statement s:c.statements){
			s.accept(q);
		}
		
		//skok na dalsi case switch-u
		v="br " +" label %"+ dalsi + "\n";
		pis(wr,v);
		
		CaseLine t = new CaseLine(result,zaciatok);
		moje.add(t);

	}

}
