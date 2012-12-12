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
	
	public CodeGenStatementVisitor(OutputStreamWriter wr,LabelGenerator l){
		this.l=l;
		this.wr=wr;
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
		ExpressionCodeGenerator g=new ExpressionCodeGenerator();
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
		String v ="br label "+BreakSkok;
		pis(wr,v);

	}

	@Override
	public void visit(ContinueStatement s) {
		// TODO Auto-generated method stub
		if(ContinueSkok == null) throw new SemanticException("Continue mimo cyklu.");
		String v ="br label "+ContinueSkok;
		pis(wr,v);

	}

	@Override
	public void visit(DowhileStatement s) {
		// TODO Auto-generated method stub
		String result;
		String typ;
		ContinueSkok=l.next();
		BreakSkok=l.next();
		String v =ContinueSkok+":\n";
		pis(wr,v);
		s.body.accept(this);
		ExpressionCodeGenerator g=new ExpressionCodeGenerator();
		s.condition.accept(g);
		result=g.GetResultRegister();
		typ=g.GetResultTyp();
		String cond=null; //TODO z type a result urobit este porovnavanie s nulou tusim
		v="br i1" + cond + ", label " + ContinueSkok + " label, "+ BreakSkok + "\n";
		pis(wr,v);
		v=BreakSkok + ": \n";
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
		String v ="";
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
