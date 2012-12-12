package codegen;

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
import statements.BlockStatement;
import statements.BreakStatement;
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

public class LabelVisitor implements StatementVisitor,ExpressionVisitor {
	
	//private Label g;
	
	//public LabelVisitor(Label k)

	@Override
	public void visit(ReturnStatement s) {
		// TODO Auto-generated method stub
		s.zaciatok=s.l.next();
		s.koniec=s.l.next();
		//TODO cyklus pre vsetkych synov s	
		Statement syn = null;//len pre skompilovatelnost
		syn.l=new Label(s.l.next());

	}

	@Override
	public void visit(BreakStatement s) {
		// TODO Auto-generated method stub
		s.zaciatok=s.l.next();
		s.koniec=s.l.next();

	}

	@Override
	public void visit(ContinueStatement s) {
		// TODO Auto-generated method stub
		s.zaciatok=s.l.next();
		s.koniec=s.l.next();

	}

	@Override
	public void visit(DowhileStatement s) {
		// TODO Auto-generated method stub
		s.zaciatok=s.l.next();
		s.koniec=s.l.next();

	}

	@Override
	public void visit(ForStatement s) {
		// TODO Auto-generated method stub
		s.zaciatok=s.l.next();
		s.koniec=s.l.next();

	}

	@Override
	public void visit(WhileStatement s) {
		// TODO Auto-generated method stub
		s.zaciatok=s.l.next();
		s.koniec=s.l.next();

	}

	@Override
	public void visit(SwitchStatement s) {
		// TODO Auto-generated method stub
		s.zaciatok=s.l.next();
		s.koniec=s.l.next();

	}

	@Override
	public void visit(IfStatement s) {
		// TODO Auto-generated method stub
		s.zaciatok=s.l.next();
		s.koniec=s.l.next();

	}

	@Override
	public void visit(OneexpressionStatement s) {
		// TODO Auto-generated method stub
		s.zaciatok=s.l.next();
		s.koniec=s.l.next();

	}

	@Override
	public void visit(BlockStatement s) {
		// TODO Auto-generated method stub
		s.zaciatok=s.l.next();
		s.koniec=s.l.next();

	}

	@Override
	public void visit(BinaryExpression e) {
		// TODO Auto-generated method stub
		
		
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

}
