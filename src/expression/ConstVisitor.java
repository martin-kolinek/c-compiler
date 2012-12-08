package expression;

import expression.binop.BinaryExpression;
import expression.constant.CharConstantExpression;
import expression.constant.FloatConstantExpression;
import expression.constant.IntConstantExpression;
import expression.constant.StringConstantExpression;
import expression.unop.UnaryExpression;

public class ConstVisitor implements ExpressionVisitor {
	
	private Object value;
	private boolean constant;
	
	public Object getExpValue(){
		return value;
	}
	
	public boolean expIsConstant(){
		return constant;
	}
	
	public ConstVisitor(){
		this.constant = true; //set false on non-constant
	}
	
	private void visit(Expression exp) {
		if (exp instanceof BinaryExpression){
			this.visit((BinaryExpression)exp);
		} else if (exp instanceof UnaryExpression){
			this.visit((UnaryExpression)exp);
		} else if (exp instanceof CastExpression){
			this.visit((CastExpression)exp);
		} else if (exp instanceof SizeofType){
			this.visit((SizeofType)exp);
		} else if (exp instanceof SizeofExpression){
			this.visit((SizeofExpression)exp);
		} else if (exp instanceof MemberAccessExpression){
			this.visit((MemberAccessExpression)exp);
		} else if (exp instanceof MemberDereferenceExpression){
			this.visit((MemberDereferenceExpression)exp);
		} else if (exp instanceof IndexingExpression){
			this.visit((IndexingExpression)exp);
		} else if (exp instanceof IDExpression){
			this.visit((IDExpression)exp);
		} else if (exp instanceof IntConstantExpression){
			this.visit((IntConstantExpression)exp);
		} else if (exp instanceof FloatConstantExpression){
			this.visit((FloatConstantExpression)exp);
		} else if (exp instanceof StringConstantExpression){
			this.visit((StringConstantExpression)exp);
		} else if (exp instanceof CharConstantExpression){
			this.visit((CharConstantExpression)exp);
		} else if (exp instanceof FunctionCallExpression){
			this.visit((FunctionCallExpression)exp);
		}
	}

	@Override
	public void visit(BinaryExpression binaryExpression) {
		this.visit(binaryExpression.left);
		Object val1 = this.value;
		this.visit(binaryExpression.right);	
		Object val2 = this.value;
		
		if (this.constant){
			
			switch (binaryExpression.operator){
				case PLUS: 
					if ((val1 instanceof Integer) && (val2 instanceof Integer)){
						//INT + INT
						this.value = new Integer(((Integer)val1) + ((Integer)val2));
					}
					if ((val1 instanceof Float) && (val2 instanceof Float)){
						//FLOAT + FLOAT
						this.value = new Float(((Float)val1) + ((Float)val2));
					}
					if ((val1 instanceof Float) && (val2 instanceof Integer)){
						//FLOAT + INT
						this.value = new Float(((Float)val1) + ((Integer)val2));
					}
					if ((val1 instanceof Integer) && (val2 instanceof Float)){
						//INT + FLOAT
						this.value = new Float(((Integer)val1) + ((Float)val2));
					}
					//pointre? Stringy?
					break;
				case MINUS: 
					if ((val1 instanceof Integer) && (val2 instanceof Integer)){
						//INT - INT
						this.value = new Integer(((Integer)val1) - ((Integer)val2));
					}
					if ((val1 instanceof Float) && (val2 instanceof Float)){
						//FLOAT - FLOAT
						this.value = new Float(((Float)val1) - ((Float)val2));
					}
					if ((val1 instanceof Float) && (val2 instanceof Integer)){
						//FLOAT - INT
						this.value = new Float(((Float)val1) - ((Integer)val2));
					}
					if ((val1 instanceof Integer) && (val2 instanceof Float)){
						//INT - FLOAT
						this.value = new Float(((Integer)val1) - ((Float)val2));
					}
					//pointre? Stringy?
					break;
				case MULT: 
					if ((val1 instanceof Integer) && (val2 instanceof Integer)){
						//INT * INT
						this.value = new Integer(((Integer)val1) * ((Integer)val2));
					}
					if ((val1 instanceof Float) && (val2 instanceof Float)){
						//FLOAT * FLOAT
						this.value = new Float(((Float)val1) * ((Float)val2));
					}
					if ((val1 instanceof Float) && (val2 instanceof Integer)){
						//FLOAT  * INT
						this.value = new Float(((Float)val1) * ((Integer)val2));
					}
					if ((val1 instanceof Integer) && (val2 instanceof Float)){
						//INT * FLOAT
						this.value = new Float(((Integer)val1) * ((Float)val2));
					}
					//pointre? Stringy?
					break;
				case DIV: 
					if ((val1 instanceof Integer) && (val2 instanceof Integer)){
						//INT / INT
						this.value = new Integer(((Integer)val1) / ((Integer)val2));
					}
					if ((val1 instanceof Float) && (val2 instanceof Float)){
						//FLOAT / FLOAT
						this.value = new Float(((Float)val1) / ((Float)val2));
					}
					if ((val1 instanceof Float) && (val2 instanceof Integer)){
						//FLOAT  / INT
						this.value = new Float(((Float)val1) / ((Integer)val2));
					}
					if ((val1 instanceof Integer) && (val2 instanceof Float)){
						//INT / FLOAT
						this.value = new Float(((Integer)val1) / ((Float)val2));
					}
					//pointre? Stringy?
					break;
				//ostatne operatory
			}
			
		} else {
			this.value = null;
		}
	}

	@Override
	public void visit() {
	}

	@Override
	public void visit(UnaryExpression unaryExpression) { //TODO skontrolovaù...
		this.visit(unaryExpression.exp);
		
		if (this.constant){
			switch (unaryExpression.op){
				case PRE_INC: 
				case PRE_DEC: 
				case POST_INC: 
				case POST_DEC:
				case PTR:
					this.constant = false;
					this.value = null;
				break;
				case ADDR:
					//TODO: only if exp is static?
					this.constant = false;
					this.value = null;
				break;
				case PLUS:
					if (this.value instanceof Integer){
						this.value = new Integer(+ ((Integer)this.value));
					}
					if (this.value instanceof Float){
						this.value = new Float(+ ((Float)this.value));
					}
				break;
				case MINUS:
					if (this.value instanceof Integer){
						this.value = new Integer(+ ((Integer)this.value));
					}
					if (this.value instanceof Float){
						this.value = new Float(+ ((Float)this.value));
					}
				break;
				case COMP:
					if (this.value instanceof Integer){
						this.value = new Integer(~ ((Integer)this.value));
					} else {
						this.value = null;
						this.constant = false;
					}
				break;
				case NOT:
					int oi;
					oi = (0 == (Integer)this.value) ? 1 : 0;
					this.value = new Integer(oi);
				break;
			}
		}
	}

	@Override
	public void visit(CastExpression castExpression) {
		// only an explicit cast of an integer constant to a pointer type, e.g (int*)0 or (unsigned*)0xbeaf.
		this.visit(castExpression.exp);
		if (this.constant){
			//if castExpression.typedecl nie je typ pointer
			this.constant = false;
			this.value = null;
		}
		
	}

	@Override
	public void visit(SizeofType sizeofType) {
		//it is ok, let it constant, only count value
		//value? nieco ako this.value = sizeof SizeofType.typedecl
		
	}

	@Override
	public void visit(SizeofExpression sizeofExpression) {
		//tiez ok, jedine ak expression je "variable length array"
		//value?
	}

	@Override
	public void visit(MemberAccessExpression memberAccessExpression) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MemberDereferenceExpression memberDereferenceExpression) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IndexingExpression indexingExpression) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IDExpression idExpression) {
		this.constant = false; //vzdycky?
	}

	@Override
	public void visit(IntConstantExpression intConstantExpression) {
		//ok, je konstanta
		this.value = new Integer(intConstantExpression.value);
	}

	@Override
	public void visit(FloatConstantExpression floatConstantExpression) {
		//ok, je konstanta
		this.value = floatConstantExpression.value;
	}

	@Override
	public void visit(StringConstantExpression stringConstantExpression) {
		//ok, je konstanta
		this.value = stringConstantExpression.value;
	}

	@Override
	public void visit(CharConstantExpression charConstantExpression) {
		//ok, je konstanta
		this.value = new Character(charConstantExpression.value);		
	}

	@Override
	public void visit(FunctionCallExpression functionCallExpression) {
		// TODO Auto-generated method stub
		this.constant = false;
		this.value = null;
	}

}
