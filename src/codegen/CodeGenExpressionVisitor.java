package codegen;

import java.io.IOException;
import java.io.OutputStreamWriter;

import types.Type;

import expression.AssignmentExpression;
import expression.CastExpression;
import expression.CommaExpression;
import expression.ExpressionVisitor;
import expression.FunctionCallExpression;
import expression.IDExpression;
import expression.IndexingExpression;
import expression.MemberAccessExpression;
import expression.MemberDereferenceExpression;
import expression.SizeofExpression;
import expression.SizeofType;
import expression.TernaryExpression;
import expression.binop.BinaryExpression;
import expression.constant.CharConstantExpression;
import expression.constant.FloatConstantExpression;
import expression.constant.IntConstantExpression;
import expression.constant.StringConstantExpression;
import expression.unop.UnaryExpression;

public class CodeGenExpressionVisitor implements ExpressionVisitor {
	
	private String Register;
	private String Typ;
	private RegisterGenerator r;
	private LabelGenerator l;
	private OutputStreamWriter wr;
	private VisitPack pack;
	
	private void pis(OutputStreamWriter o,String s){
		try {
			o.append(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CodeGenExpressionVisitor(VisitPack pack){
		this.pack=pack;
		this.l=this.pack.l;
		this.r=this.pack.r;
		this.wr=this.pack.wr;
	}
	
	public String GetResultRegister(){//TODO
		return Register;
	}
	
	public String GetResultTyp(){//TODO
		return Typ;
	}

	@Override
	public void visit(BinaryExpression e) {
		// TODO Auto-generated method stub
		String v=null;
		CodeGenExpressionVisitor v1 = new CodeGenExpressionVisitor(pack);
		CodeGenExpressionVisitor v2 = new CodeGenExpressionVisitor(pack);
		e.right.accept(v1);
		e.left.accept(v2);
		String result1 = v1.GetResultRegister();
		String result2 = v2.GetResultRegister();
		String typ1 = v1.GetResultTyp();
		String typ2 = v2.GetResultTyp();
		
		
		switch(e.operator){
		case PLUS :
			Type t=pack.t.getExpressionType(e);
			CodeGenTypeVisitor tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= add "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // +
		case MINUS : 
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= sub "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // -
		case MULT :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // *
		case DIV :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // /
		case MOD :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // %
		case BSLEFT :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // <<
		case BSRIGHT :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // >>
		case BAND :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // &
		case BXOR :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // ^
		case BOR :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break;  // |
		case AND :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // &&
		case OR :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // ||
		case GT :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // >
		case LT :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // <
		case GET :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // >=
		case LET :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // <=
		case EQ :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // ==
		case NEQ :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // !=
		case ASSIG ://TODO priradenie do pamate
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; //'='
		
		/*
		AMULT, //'*=' 
		ADIV, //'/=' 
		AMOD, //'%=' 
		APLUS, //'+=' 
		AMINUS, //'-=' 
		ABSLEFT, //'<<='
		ABSRIGHT, //'>>='
		ABAND, //'&=' 
		ABXOR, // '^=' 
		ABOR, */
		}

	}

	@Override
	public void visit(UnaryExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CastExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SizeofType e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SizeofExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MemberAccessExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MemberDereferenceExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IndexingExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IDExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IntConstantExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(FloatConstantExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StringConstantExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CharConstantExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(FunctionCallExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TernaryExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CommaExpression e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AssignmentExpression e) {
		// TODO Auto-generated method stub
		
	}

}
