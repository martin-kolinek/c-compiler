package codegen;

import java.io.OutputStreamWriter;
import java.util.HashMap;

import declaration.Declaration;
import declaration.ResolvedDeclaration;
import declaration.TypedefDeclaration;
import statements.Statement;
import toplevel.FunctionDefinition;
import toplevel.FunctionParameter;
import toplevel.InBlockVisitor;
import typeresolve.ExpressionTypeMapping;
import types.PrimitiveType;

public class MainCodeGenVisitor implements InBlockVisitor {
	public BlockCodeGenerator cg;
	private HashMap<String, String> funcDecls;

	public MainCodeGenVisitor(OutputStreamWriter sw, ExpressionTypeMapping mp) {
		this.cg=new BlockCodeGenerator(new CodeGenStream(sw), mp, new LabelGenerator("%lbl."), new RegisterGenerator("%reg."), new RegisterGenerator("@glob."));
		funcDecls = new HashMap<String, String>();
	}

	@Override
	public void visit(Statement i) {
		assert false;//tieto by na globalnej urovni nemali byt
	}

	@Override
	public void visit(Declaration i) {
		assert false;
	}

	@Override
	public void visit(FunctionDefinition i) {
		StringBuilder sb = new StringBuilder();
		sb.append(i.body==null?"declare":"define");
		sb.append(" ");
		sb.append(cg.getTypeString(i.returnType));
		sb.append(" ");
		sb.append("@"+i.name);
		sb.append("(");
		boolean f=true;
		for(FunctionParameter p: i.parameters) {
			if(!f)
				sb.append(",");
			f=false;
			sb.append(cg.getTypeString(p.type));
			sb.append(" ");
			if(i.body!=null) {
				sb.append("%par."+p.id);
			}
		}
		if(i.variadic) {
			if(!f)
				sb.append(",");
			sb.append("...");
		}
		sb.append(")\n");
		if(i.body!=null) {
			funcDecls.remove(i.name);
			cg.str.writeLine(sb.toString());
			cg.str.writeLine("{");
			BlockCodeGenerator icg = new BlockCodeGenerator(cg);
			for(FunctionParameter p:i.parameters) {
				String reg = icg.getNextregister();
				icg.storeID(p.id, reg);
				icg.str.writeAssignment(reg, "alloca", icg.getTypeString(p.type));
				icg.str.writeLine("store", icg.getTypeString(p.type), "%par."+p.id, ",", icg.getTypeString(p.type)+"*", reg);
				
			}
			icg.generateStatement(i.body);
			if(i.returnType==PrimitiveType.VOID)
				cg.str.writeLine("ret void");
			else {
				String ret = cg.getNextregister();
				cg.str.writeAssignment(ret, "inttoptr i64 0 to ", cg.getTypeString(i.returnType)+"*");
				String ret2 = cg.getNextregister();
				cg.str.writeAssignment(ret2, "load", cg.getTypeString(i.returnType)+"*", ret);
				cg.str.writeLine("ret", cg.getTypeString(i.returnType), ret2);
			}
			cg.str.writeLine("}");
		}
		else {
			funcDecls.put(i.name, sb.toString());
		}
	}

	@Override
	public void visit(TypedefDeclaration i) {
		assert false;
	}

	@Override
	public void visit(ResolvedDeclaration i) {
		CodeGenGlobalInitializer ini = new CodeGenGlobalInitializer(cg);
		i.accept(ini);
	}
	
	public void finalize() {
		cg.generateStrings();
		for(String v:funcDecls.values()) {
			cg.str.writeLine(v);
		}
	}

}
