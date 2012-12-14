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
	
	public VisitPack pack;
	public String BreakSkok;
	public String ContinueSkok;
	LabelGenerator l;
	RegisterGenerator r;
	
	public CodeGenStatementVisitor(VisitPack pack){
		this.pack=pack;
		this.l=this.pack.l;
		this.r=this.pack.r;
		this.wr=this.pack.wr;
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
		CodeGenExpressionVisitor g=new CodeGenExpressionVisitor(pack);
		s.exp.accept(g);
		result=g.GetResultRegister();
		typ=g.GetResultTyp();
		String v ="ret "+typ+" "+result+'\n';
		pis(wr,v);
				
	}

	@Override
	public void visit(BreakStatement s) {
		if(BreakSkok == null) throw new SemanticException("Break mimo cyklu.");
		String v ="br "+" label %"+BreakSkok;
		pis(wr,v);

	}

	@Override
	public void visit(ContinueStatement s) {
		if(ContinueSkok == null) throw new SemanticException("Continue mimo cyklu.");
		String v ="br "+" label %"+ContinueSkok;
		pis(wr,v);

	}

	@Override
	public void visit(DowhileStatement s) {
		
		String v=null;
		pis(wr,v);

	}

	@Override
	public void visit(ForStatement s) {
		//nerobi sa, konvertuje sa na while
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
		CodeGenExpressionVisitor g=new CodeGenExpressionVisitor(pack);
		s.condition.accept(g);
		result=g.GetResultRegister();
		typ=g.GetResultTyp();
		v=r.next() + "= icmp ne " + typ + " " + Integer.toString(0) + ", " + result + "\n"; 
		pis(wr,v);
		v="br i1" + r.akt() + ", " + " label %"+DalejSkok + ", "+" label %"+ BreakSkok + "\n";
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
		
		String Koniec = l.next();//default label, ak nic nematch-ne
		CodeGenExpressionVisitor g=new CodeGenExpressionVisitor(pack);
		s.expr.accept(g);
		String result = g.GetResultRegister();
		String typ = g.GetResultTyp(); 
		
		//toto ma predpocitat jednotlive hodnoty case-u a zozbierat pre ne nazvy docas premennych
		
		VisitPack p=new VisitPack(wr,l,r,this.pack.table);
		CodeGenCaseVisitor z=new CodeGenCaseVisitor(p);
		z.Koniec=Koniec;
		z.dalsi=l.next();
		String inak = l.next();
		
		for (Case c : s.cases){
			z.zaciatok=z.dalsi;
			z.dalsi=l.next();
			c.accept(z);
		}
		String v = z.dalsi + ":\n";
		pis(wr,v);
		v="br "+" label %"+inak + "\n"; 
		
		v = inak +":\n";
		pis(wr,v);
		
		p = new VisitPack(wr,l,r,this.pack.table);
		CodeGenStatementVisitor f = new CodeGenStatementVisitor(p);
		f.BreakSkok=Koniec;
		for (Statement  d: s.def){
			d.accept(f);
		}
		
		//podsunut visitoru label na koniec switch
		v="br " +" label %"+ Koniec + "\n";
		pis(wr,v);
		
		v ="switch " + typ + " " + result+ ", " +" label %"+ inak + " [ \n";
		pis(wr,v);
		
		// vypis predpocitanych options a labelov na statementy na ne
		for (CaseLine d:z.moje){
			v=typ + " " + d.expr + ", "+ " label %"+d.label + "\n";
			pis(wr,v);
		}		
		
		v = "]\n";
		pis(wr,v);
		v= Koniec + "\n";//label na konci switch-u
		pis(wr,v);

	}

	@Override
	public void visit(IfStatement s) {
		
		//podmienka ifu
		CodeGenExpressionVisitor g = new CodeGenExpressionVisitor(pack);
		s.cond.accept(g);
		String result = g.GetResultRegister();
		String typ = g.GetResultTyp(); 
		
		//inicializacia labelov
		String Koniec = l.next();
		String PrvaVetva = l.next();
		String DruhaVetva = l.next();
		
		//vyhodnotenie podmienky
		String v = r.next() + "= icmp ne " + typ + " " + Integer.toString(0) + ", " + result + "\n"; 
		pis(wr,v);
		v="br i1" + r.akt() + ", " +" label %"+ PrvaVetva + ", "+" label %"+ DruhaVetva + "\n";
		pis(wr,v);
		
		//prva vetva if-u
		v = PrvaVetva + ":\n";
		pis(wr,v);
		s.ontrue.accept(this);
		
		//skok na koniec
		v="br " +" label %"+ Koniec + "\n";
		
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
		CodeGenExpressionVisitor g = new CodeGenExpressionVisitor(pack);
		s.expr.accept(g);

	}

	@Override
	public void visit(BlockStatement s) {
		// TODO Auto-generated method stub
		String v ="";
		pis(wr,v);

	}

}
