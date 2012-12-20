package codegen;

import java.io.OutputStreamWriter;
import declaration.Declaration;
import declaration.ResolvedDeclaration;
import declaration.TypedefDeclaration;
import statements.Statement;
import toplevel.FunctionDefinition;
import toplevel.FunctionParameter;
import toplevel.InBlockVisitor;
import typeresolve.ExpressionTypeMapping;

public class MainCodeGenVisitor implements InBlockVisitor {
	public BlockCodeGenerator cg;

	public MainCodeGenVisitor(OutputStreamWriter sw, ExpressionTypeMapping mp) {
		this.cg=new BlockCodeGenerator(new CodeGenStream(sw), mp, new LabelGenerator("%lbl."), new RegisterGenerator("%reg."), new RegisterGenerator("@glob."));
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
		cg.str.write(i.body==null?"declare":"define");
		cg.str.write(cg.getTypeString(i.returnType));
		cg.str.write("@"+i.name);
		cg.str.write("(");
		boolean f=true;
		for(FunctionParameter p: i.parameters) {
			if(!f)
				cg.str.write(",");
			f=false;
			cg.str.write(cg.getTypeString(p.type));
			if(i.body!=null) {
				cg.str.write("%par."+p.id);
			}
		}
		if(i.variadic) {
			if(!f)
				cg.str.write(",");
			cg.str.write("...");
		}
		cg.str.write(")\n");
		if(i.body!=null) {
			cg.str.writeLine("{");
			BlockCodeGenerator icg = new BlockCodeGenerator(cg);
			for(FunctionParameter p:i.parameters) {
				String reg = icg.getNextregister();
				icg.storeID(p.id, reg);
				icg.str.writeAssignment(reg, "alloca", icg.getTypeString(p.type));
				icg.str.writeLine("store", icg.getTypeString(p.type), "%par."+p.id, ",", icg.getTypeString(p.type)+"*", reg);
				
			}
			icg.generateStatement(i.body);
			cg.str.writeLine("}");
		}
	}

	@Override
	public void visit(TypedefDeclaration i) {
		assert false;
	}

	@Override
	public void visit(ResolvedDeclaration i) {
		CodeGenGlobalInitializer ini = new CodeGenGlobalInitializer();
		i.accept(ini);
	}

}
