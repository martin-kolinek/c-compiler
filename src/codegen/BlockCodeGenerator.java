package codegen;

import java.io.OutputStreamWriter;

import expression.Expression;

import statements.Statement;
import symbols.SymbolTable;
import typeresolve.ExpressionTypeMapping;
import types.Type;

public class BlockCodeGenerator {
	public BlockCodeGenerator(OutputStreamWriter sw){
		str = new CodeGenStream(sw);
	}
	
	public BlockCodeGenerator(CodeGenStream str2,
			ExpressionValueMapping valmap2, ExpressionTypeMapping typemap2,
			SymbolTable<String> idAddresses2, LabelGenerator lg2,
			RegisterGenerator rg2) {
		this.str=str2;
		this.lg=lg2;
		this.rg=rg2;
		this.typemap=typemap2;
		this.valmap=valmap2;
		this.idAddresses=new SymbolTable<String>(idAddresses2);
		// TODO Auto-generated constructor stub
	}

	public final CodeGenStream str;
	private ExpressionValueMapping valmap;
	private ExpressionTypeMapping typemap;
	private SymbolTable<String> idAddresses;
	private LabelGenerator lg;
	private RegisterGenerator rg;
	private RegisterGenerator grg;
	private StringConstantCodeGen strings;
	
	public String getExpressionRegister(Expression e){
		return valmap.getExpressionResult(e, new CodeGenExpressionVisitor(this));
	}
	
	public String getNextLabel() {
		return lg.next();
	}
	
	public String getNextregister() {
		return rg.next();
	}
	
	public String getNextGlobalRegister() {
		return grg.next();
	}
	
	public String getExpressionAddress(Expression ex) {
		CodeGenExpressionAddress adr = new CodeGenExpressionAddress(this);
		ex.accept(adr);
		return adr.getResult();
	}
	
	public Type getExpressionType(Expression exp) {
		return typemap.getExpressionType(exp);
	}
	
	public String getTypeString(Type t) {
		CodeGenTypeVisitor tv = new CodeGenTypeVisitor(this, false);
		t.accept(tv);
		return tv.GetTypeText();
	}
	
	//this one makes real arrays instead of pointers
	public String getProperTypeString(Type t) { 
		CodeGenTypeVisitor tv = new CodeGenTypeVisitor(this, true);
		t.accept(tv);
		return tv.GetTypeText();
	}
	
	public String getExpressionTypeStr(Expression e) {
		return getTypeString(getExpressionType(e));
	}
	
	public String getIDAddress(String id) {
		String addr = idAddresses.get(id);
		if(isGlobalArray(id)) {
			String tmp = getNextregister();
			str.writeAssignment(tmp, "getelementptr", getGlobalArrayTypeString(id)+"*", getIDAddress(id), ",", "i32 0, i32 0");
			addr = tmp;
		}
		return addr;
	}
	
	public void generateStatement(Statement st) {
		CodeGenStatementVisitor vis = new CodeGenStatementVisitor(this);
		st.accept(vis);
	}
	
	public boolean isGlobalArray(String id) {
		return false; //TODO
	}
	
	public String getGlobalArrayTypeString(String id) {
		return ""; //TODO
	}
	
	public String getStringAddress(byte[] value) {
		return strings.addStringConstant(value);
	}
	
	public String getSizeOfResult(Type t) {
		CodeGenSizeOfVisitor sv = new CodeGenSizeOfVisitor(this);
		t.accept(sv);
		return sv.getResult();
	}

	public BlockCodeGenerator getChild() {
		// TODO Auto-generated method stub
		return new BlockCodeGenerator(str,valmap,typemap,idAddresses,lg,rg);
	}
}
