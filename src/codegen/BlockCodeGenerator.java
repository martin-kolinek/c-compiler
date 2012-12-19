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
	
	public final CodeGenStream str;
	private ExpressionValueMapping valmap;
	private ExpressionTypeMapping typemap;
	private SymbolTable<String> idAddresses;
	private LabelGenerator lg;
	private RegisterGenerator rg;
	
	public String getExpressionRegister(Expression e){
		return valmap.getExpressionResult(e, new CodeGenExpressionVisitor(this));
	}
	
	public String getNextLabel() {
		return lg.next();
	}
	
	public String getNextregister() {
		return rg.next();
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
		CodeGenTypeVisitor tv = new CodeGenTypeVisitor(new VisitPack(str, lg, rg));
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
}
