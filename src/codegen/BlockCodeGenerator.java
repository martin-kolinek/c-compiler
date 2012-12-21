package codegen;

import java.io.OutputStreamWriter;
import java.util.HashMap;
import expression.Expression;

import statements.Statement;
import symbols.SymbolTable;
import toplevel.FunctionDefinition;
import toplevel.FunctionParameter;
import typeresolve.ExpressionTypeMapping;
import types.Type;

public class BlockCodeGenerator {
	public BlockCodeGenerator(OutputStreamWriter sw){
		str = new CodeGenStream(sw);
	}
	
	public BlockCodeGenerator(CodeGenStream str2,
			ExpressionTypeMapping typemap2,
			LabelGenerator lg2,
			RegisterGenerator rg2,
			RegisterGenerator grg2) {
		this.str=str2;
		this.lg=lg2;
		this.rg=rg2;
		this.grg=grg2;
		this.typemap=typemap2;
		this.valmap=new ExpressionValueMapping();
		this.idAddresses=new SymbolTable<String>();
		this.strings = new StringConstantCodeGen(this);
		this.globArrays=new HashMap<String, Type>();
	}
	
	public BlockCodeGenerator(BlockCodeGenerator parent) {
		this.str = parent.str;
		this.valmap = parent.valmap;
		this.typemap=parent.typemap;
		this.idAddresses=parent.idAddresses;//??? nema tu byt: new SymbolTable<String>(parent.idAddresses); ???
		this.lg=parent.lg;
		this.rg=parent.rg;
		this.grg=parent.grg;
		this.strings=parent.strings;
		this.globArrays=parent.globArrays;
	}

	public final CodeGenStream str;
	private ExpressionValueMapping valmap;
	private ExpressionTypeMapping typemap;
	private SymbolTable<String> idAddresses;
	private LabelGenerator lg;
	private RegisterGenerator rg;
	private RegisterGenerator grg;
	private StringConstantCodeGen strings;
	private HashMap<String, Type> globArrays;
	
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
		return globArrays.containsKey(idAddresses.get(id));
	}
	
	public void setGlobalArray(String id, Type t) {
		globArrays.put(idAddresses.get(id), t);
	}
	
	public String getGlobalArrayTypeString(String id) {
		return getProperTypeString(globArrays.get(idAddresses.get(id)));
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
		return new BlockCodeGenerator(this);
	}
	
	public void storeID(String id, String register) {
		idAddresses.store(id, register);
	}
	
	public void generateStrings() {
		strings.writeOutput();
	}
	
	public String generateFunctionType(FunctionDefinition def) {
		StringBuilder ret = new StringBuilder();
		ret.append(getTypeString(def.returnType));
		ret.append("(");
		boolean first = true;
		for(FunctionParameter p:def.parameters) {
			if(!first)
				ret.append(", ");
			first = false;
			ret.append(getTypeString(p.type));
		}
		if(def.variadic) {
			if(!first)
				ret.append(", ");
			ret.append("...");
		}
		ret.append(")*");
		return ret.toString();
	}
}
