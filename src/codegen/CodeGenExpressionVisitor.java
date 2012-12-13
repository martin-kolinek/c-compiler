package codegen;

import java.io.IOException;
import java.io.OutputStreamWriter;

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
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= add "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // +
		case MINUS : 
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= sub "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // -
		case MULT :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // *
		case DIV :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // /
		case MOD :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // %
		case BSLEFT :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // <<
		case BSRIGHT :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // >>
		case BAND :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // &
		case BXOR :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // ^
		case BOR :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break;  // |
		case AND :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // &&
		case OR :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // ||
		case GT :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // >
		case LT :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // <
		case GET :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // >=
		case LET :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // <=
		case EQ :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // ==
		case NEQ :
			Typ=null;//TODO z typ1 a typ2
			Register=pack.l.next();
			v=Register + "= TODO "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // !=
		case ASSIG ://TODO priradenie do pamate
			Typ=null;//TODO z typ1 a typ2
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
