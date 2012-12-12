package codegen;

import java.io.IOException;
import java.io.OutputStreamWriter;

import exceptions.SemanticException;

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
	
	private String BreakSkok;
	private String ContinueSkok;
	LabelGenerator l;
	RegisterGenerator r;
	
	public CodeGenStatementVisitor(OutputStreamWriter wr,LabelGenerator l,RegisterGenerator r){
		this.l=l;
		this.wr=wr;
		this.r=r;
	}
	
	private void pis(OutputStreamWriter o,String s){
		try {
			o.append(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void visit(ReturnStatement s) {
		// TODO Auto-generated method stub
		String result;
		String typ;
		CodeGenExpressionVisitor g=new CodeGenExpressionVisitor(r);
		s.exp.accept(g);
		result=g.GetResultRegister();
		typ=g.GetResultTyp();
		String v ="ret "+typ+" "+result+'\n';
		pis(wr,v);
				
	}

	@Override
	public void visit(BreakStatement s) {
		// TODO Auto-generated method stub
		if(BreakSkok == null) throw new SemanticException("Break mimo cyklu.");
		String v ="br "+BreakSkok;
		pis(wr,v);

	}

	@Override
	public void visit(ContinueStatement s) {
		// TODO Auto-generated method stub
		if(ContinueSkok == null) throw new SemanticException("Continue mimo cyklu.");
		String v ="br "+ContinueSkok;
		pis(wr,v);

	}

	@Override
	public void visit(DowhileStatement s) {
		// TODO Auto-generated method stub
		
		//inicializacia
		String result;
		String typ;
		ContinueSkok=l.next();
		BreakSkok=l.next();
		
		//label na zaciatok cyklu
		String v =ContinueSkok+":\n";
		pis(wr,v);
		
		//telo cyklu
		s.body.accept(this);
		
		//podmienka cyklu
		CodeGenExpressionVisitor g=new CodeGenExpressionVisitor(r);
		s.condition.accept(g);
		result=g.GetResultRegister();
		typ=g.GetResultTyp();
		
		//rozhodovanie cyklu
		v=r.next() + "= icmp ne " + typ + " " /*TODO + pretypovana(0) */+ " " + result + "\n"; 
		pis(wr,v);
		v="br i1" + r.akt() + ", " + ContinueSkok + ", "+ BreakSkok + "\n";
		pis(wr,v);
		
		v=" " + BreakSkok + ": \n";
		pis(wr,v);

	}

	@Override
	public void visit(ForStatement s) {
		// TODO Auto-generated method stub
		String v ="";
		pis(wr,v);

	}

	@Override
	public void visit(WhileStatement s) {
		// TODO Auto-generated method stub
		
		//inicializacia
		String result;
		String typ;		
		ContinueSkok=l.next();
		BreakSkok=l.next();
		String DalejSkok = l.next();
		
		//label zaciatku cyklu
		String v =ContinueSkok+":\n";
		pis(wr,v);
		
		//podmienka cyklu
		CodeGenExpressionVisitor g=new CodeGenExpressionVisitor(r);
		s.condition.accept(g);
		result=g.GetResultRegister();
		typ=g.GetResultTyp();
		v=r.next() + "= icmp ne " + typ + " " /*TODO + pretypovana(0) */+ " " + result + "\n"; 
		pis(wr,v);
		v="br i1" + r.akt() + ", " + DalejSkok + ", "+ BreakSkok + "\n";
		pis(wr,v);
		
		//pokracovanie v cykle
		v=DalejSkok + ": \n";
		pis(wr,v);
		
		//vykona sa telo cyklu a skace sa za podmienku
		s.body.accept(this);
		v="br " + ContinueSkok + "\n";
		pis(wr,v);
		
		//label za cyklom
		v=BreakSkok + ": \n";
		pis(wr,v);

	}

	@Override
	public void visit(SwitchStatement s) {
		// TODO Auto-generated method stub
		String v ="";
		pis(wr,v);

	}

	@Override
	public void visit(IfStatement s) {
		// TODO Auto-generated method stub
		String v ="";
		pis(wr,v);

	}

	@Override
	public void visit(OneexpressionStatement s) {
		// TODO Auto-generated method stub
		String v ="";
		pis(wr,v);

	}

	@Override
	public void visit(BlockStatement s) {
		// TODO Auto-generated method stub
		String v ="";
		pis(wr,v);

	}

}
