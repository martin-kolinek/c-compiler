package codegen;

import java.io.IOException;
import java.io.OutputStreamWriter;

import exceptions.SemanticException;

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
		String result;
		String typ;
		CodeGenExpressionVisitor g=new CodeGenExpressionVisitor(wr,l,r);
		s.exp.accept(g);
		result=g.GetResultRegister();
		typ=g.GetResultTyp();
		String v ="ret "+typ+" "+result+'\n';
		pis(wr,v);
				
	}

	@Override
	public void visit(BreakStatement s) {
		if(BreakSkok == null) throw new SemanticException("Break mimo cyklu.");
		String v ="br "+BreakSkok;
		pis(wr,v);

	}

	@Override
	public void visit(ContinueStatement s) {
		if(ContinueSkok == null) throw new SemanticException("Continue mimo cyklu.");
		String v ="br "+ContinueSkok;
		pis(wr,v);

	}

	@Override
	public void visit(DowhileStatement s) {
		
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
		CodeGenExpressionVisitor g=new CodeGenExpressionVisitor(wr,l,r);
		s.condition.accept(g);
		result=g.GetResultRegister();
		typ=g.GetResultTyp();
		
		//rozhodovanie cyklu
		v=r.next() + "= icmp ne " + typ + " "  + Integer.toString(0) + " " + result + "\n"; 
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
		CodeGenExpressionVisitor g=new CodeGenExpressionVisitor(wr,l,r);
		s.condition.accept(g);
		result=g.GetResultRegister();
		typ=g.GetResultTyp();
		v=r.next() + "= icmp ne " + typ + " " + Integer.toString(0) + " " + result + "\n"; 
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
		
		String Koniec = l.next();//default label, ak nic nematch-ne
		CodeGenExpressionVisitor g=new CodeGenExpressionVisitor(wr,l,r);
		s.expr.accept(g);
		String result = g.GetResultRegister();
		String typ = g.GetResultTyp(); 
		
		//toto ma predpocitat jednotlive hodnoty case-u a zozbierat pre ne nazvy docas premennych
		CodeGenCaseVisitor z=new CodeGenCaseVisitor(wr,l,r);
		z.Koniec=Koniec;
		
		for (Case c : s.cases){
			c.accept(z);//TODO
		}
		//TODO podsunut visitoru label na koniec switch, pre istotu
		for (Statement  d: s.def){
			d.accept(null);//TODO
		}
		
		String v ="switch " + typ + " " + result+ ", " + Koniec + " [ \n";
		pis(wr,v);
		
		//TODO prechadzanie pola moznosti
		//sem pojde vypis predpocitanych options a labelov na statementy na ne
		
		v = "]\n";
		pis(wr,v);
		v= Koniec + "\n";//label na konci switch-u
		pis(wr,v);

	}

	@Override
	public void visit(IfStatement s) {
		// TODO Auto-generated method stub
		
		//podmienka ifu
		CodeGenExpressionVisitor g = new CodeGenExpressionVisitor(wr,l,r);
		s.cond.accept(g);
		String result = g.GetResultRegister();
		String typ = g.GetResultTyp(); 
		
		//inicializacia labelov
		String Koniec = l.next();
		String PrvaVetva = l.next();
		String DruhaVetva = l.next();
		
		//vyhodnotenie podmienky
		String v = r.next() + "= icmp ne " + typ + " " + Integer.toString(0) + " " + result + "\n"; 
		pis(wr,v);
		v="br i1" + r.akt() + ", " + PrvaVetva + ", "+ DruhaVetva + "\n";
		pis(wr,v);
		
		//prva vetva if-u
		v = PrvaVetva + ":\n";
		pis(wr,v);
		s.ontrue.accept(this);
		
		//skok na koniec
		v="br " + Koniec + "\n";
		
		//druha vetva if-u
		v=DruhaVetva+ ":\n";
		pis(wr,v);
		s.onfalse.accept(this);
		
		//vypis label-u konca		
		v =Koniec + ":\n";
		pis(wr,v);

	}

	@Override
	public void visit(OneexpressionStatement s) {
		CodeGenExpressionVisitor g = new CodeGenExpressionVisitor(wr,l,r);
		s.expr.accept(g);

	}

	@Override
	public void visit(BlockStatement s) {
		// TODO Auto-generated method stub
		String v ="";
		pis(wr,v);

	}

}
