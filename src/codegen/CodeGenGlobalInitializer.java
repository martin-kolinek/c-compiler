package codegen;

import toplevel.EmptyInBlockVisitor;
import types.StructType;
import types.TypeClass;
import declaration.ResolvedDeclaration;
import declaration.initializer.CompoundInitializer;
import declaration.initializer.ExpressionInitializer;

public class CodeGenGlobalInitializer extends EmptyInBlockVisitor {

	BlockCodeGenerator cg;
	
	@Override
	public void visit(ResolvedDeclaration i) {// TODO aby sa nezabudlo
												// pouzatvarat zatvorky
		String adr = cg.getNextGlobalRegister();
		cg.str.write(adr + "= global");
		cg.storeID(i.identifier, adr);
		if(TypeClass.isArray(i.type))
			cg.setGlobalArray(i.identifier, i.type);
		String Typ = cg.getTypeString(i.type);
		cg.str.write(Typ);
		if (i.initializer == null) {
			cg.str.writeLine("zeroinitializer");
		} else if (i.initializer instanceof ExpressionInitializer) {
			ExpressionInitializer j = (ExpressionInitializer) i.initializer;
			String exp = cg.getExpressionRegister(j.exp);
			cg.str.write(exp);
		} else if (i.initializer instanceof CompoundInitializer) {
			cg.str.write("{");
			StructType t = (StructType) i.type;
			CodeGenInitializerVisitor iv = new CodeGenInitializerVisitor(cg,t);
			i.initializer.accept(iv);
			cg.str.write("}\n");

		}
	}

}