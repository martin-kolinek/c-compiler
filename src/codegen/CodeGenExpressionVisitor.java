package codegen;

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
	private String Typ = "";
//	private RegisterGenerator r;
//	private LabelGenerator l;
//	private OutputStreamWriter wr;
	private VisitPack pack;
	private boolean acces = false;//TODO je to premenna z pamate, ktoru treba po uprave ulozit
	private String adress = "";//TODO na tuto poziciu
	
	private void pis(CodeGenStream o,String s){
		o.writeLine(new String[]{s});
	}
	
	public CodeGenExpressionVisitor(VisitPack pack){
		this.pack=pack;
//		this.l=this.pack.l;
//		this.r=this.pack.r;
//		this.wr=this.pack.wr;
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
		e.left.accept(v1);
		e.right.accept(v2);
		String result1 = v1.GetResultRegister();
		String result2 = v2.GetResultRegister();
		
		switch(e.operator){
		
		case PLUS :
			Type t=pack.t.getExpressionType(e);
			CodeGenTypeVisitor tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();
			Register=pack.r.next();
			
			if(tv.integer())
				v=Register + "= add "+ Typ + " " + result1 + ", " + result2 + "\n";
			else//je float
				v=Register + "= fadd "+ Typ + " " + result1 + ", " + result2 + "\n";
			
			pis(pack.wr,v);
			break; // +
		
		case MINUS : 
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();;
			Register=pack.r.next();
			
			if(tv.integer())
				v=Register + "= sub "+ Typ + " " + result1 + ", " + result2 + "\n";
			else
				v=Register + "= fsub "+ Typ + " " + result1 + ", " + result2 + "\n";
			
			pis(pack.wr,v);
			break; // -
		
		case MULT :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();
			Register=pack.r.next();
			
			if(tv.integer())
				v=Register + "= mul "+ Typ + " " + result1 + ", " + result2 + "\n";
			else
				v=Register + "= fmul "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // *
		
		case DIV :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();
			Register=pack.r.next();
			if(tv.integer())
				v=Register + "= sdiv "+ Typ + " " + result1 + ", " + result2 + "\n";
			else
				v=Register + "= fdiv "+ Typ + " " + result1 + ", " + result2 + "\n";			pis(pack.wr,v);
			break; // /
		
		case MOD :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();
			Register=pack.r.next();
			v=Register + "= srem "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // %
		
		case BSLEFT :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();
			Register=pack.r.next();
			v=Register + "= shl "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // <<
		
		case BSRIGHT :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();
			Register=pack.r.next();
			v=Register + "= lshr "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // >>
		
		case BAND :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();
			Register=pack.r.next();
			v=Register + "= and "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // &
			
		case BXOR :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();
			Register=pack.r.next();
			v=Register + "= xor "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // ^
			
		case BOR :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();
			Register=pack.r.next();
			v=Register + "= or "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break;  // |
			
		case AND :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();
			Register=pack.r.next();
			v=pack.r.next() + "= and "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			v = Register + "= icmp ne " + Typ + " " + Integer.toString(0) + ", " + pack.r.akt() + "\n";
			pis(pack.wr,v);
			break; // &&
			
		case OR :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();
			Register=pack.r.next();
			v=pack.r.next() + "= or "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			v = Register + "= icmp ne " + Typ + " " + Integer.toString(0) + ", " + pack.r.akt() + "\n";
			pis(pack.wr,v);
			break; // ||
			
		case GT :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();
			Register=pack.r.next();
			if(tv.unsig())
				v=Register + "= icmp ugt "+ Typ + " " + result1 + ", " + result2 + "\n";
			else
				v=Register + "= icmp sgt "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // >
			
		case EQ :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();
			Register=pack.r.next();
			v=Register + "= icmp eq "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // ==
		
		case NEQ :
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();
			Register=pack.r.next();
			v=Register + "= icmp nq "+ Typ + " " + result1 + ", " + result2 + "\n";
			pis(pack.wr,v);
			break; // !=
		
		case ASSIG ://TODO priradenie do pamate
			t=pack.t.getExpressionType(e);
			tv = new CodeGenTypeVisitor(pack);
			t.accept(tv);
			Typ=tv.GetTypeText();//TODO z typ1 a typ2
			Register=pack.r.next();
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
		
		CodeGenExpressionVisitor v1 = new CodeGenExpressionVisitor(pack);
		e.exp.accept(v1);
		String result1 = v1.GetResultRegister();
		Type t = pack.t.getExpressionType(e);
		CodeGenTypeVisitor tv = new CodeGenTypeVisitor(pack);
		t.accept(tv);
		Typ=tv.GetTypeText();
		
		switch(e.op){
			case PRE_INC :
				Register=pack.r.next();
				//pouzijeme prislusnu operaciu scitovania
				if(tv.integer())
					pack.wr.writeAssignment(Register, "add",Typ, "1", result1);
				else
					pack.wr.writeAssignment(Register, "fadd",Typ, "1", result1);
				//ak islo o premennu z pamate, ulozime inkrementovanu hodnotu
				if (v1.acces()) pack.wr.store(v1.adress(),Typ,Register);
				break; //++
			case PRE_DEC :
				Register=pack.r.next();
				//pouzijeme prislusnu operaciu odcitovania
				if(tv.integer())
					pack.wr.writeAssignment(Register, "sub",Typ, "1", result1);
				else
					pack.wr.writeAssignment(Register, "fsub",Typ, "1", result1);
				//ak islo o premennu z pamate, ulozime dekrementovanu hodnotu
				if (v1.acces()) pack.wr.store(v1.adress(),Typ,Register);
				break; //--
			case POST_INC :
				Register=pack.r.next();
				//posleme neinkrementovanu hodnotu
				pack.wr.writeAssignment(Register, Typ,result1);
				String res = pack.r.next();
				//pouzijeme prislusnu operaciu scitovania
				if(tv.integer())
					pack.wr.writeAssignment(res, "add",Typ, "1", result1);
				else
					pack.wr.writeAssignment(res, "fadd",Typ, "1", result1);
				//ak islo o premennu z pamate, ulozime inkrementovanu hodnotu
				if (v1.acces()) pack.wr.store(v1.adress(),Typ,res);
				break; //++
			case POST_DEC :
				Register=pack.r.next();
				//posleme nedekrementovanu hodnotu
				pack.wr.writeAssignment(Register, Typ,result1);
				res = pack.r.next();
				//pouzijeme prislusnu operaciu odcitovania
				if(tv.integer())
					pack.wr.writeAssignment(res, "sub",Typ, "1", result1);
				else
					pack.wr.writeAssignment(res, "fsub",Typ, "1", result1);
				//ak islo o premennu z pamate, ulozime dekrementovanu hodnotu
				if (v1.acces()) pack.wr.store(v1.adress(),Typ,res);
				break; //--
			case ADDR :
				break; //&
			case PTR :
				break; //*
			case COMP :
				Register=pack.r.next();
				pack.wr.writeAssignment(Register, "xor",Typ, "-1", result1);
				break; //~ 
			case NOT :
				break; //!
		
		}

	}

	private String adress() {
		// TODO Auto-generated method stub
		return adress;
	}

	private boolean acces() {
		// TODO Auto-generated method stub
		return acces ;
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
