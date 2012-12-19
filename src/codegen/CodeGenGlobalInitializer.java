package codegen;

import symbols.SymbolTable;
import toplevel.EmptyInBlockVisitor;
import declaration.ResolvedDeclaration;
import declaration.initializer.CompoundInitializer;
import declaration.initializer.ExpressionInitializer;

public class CodeGenGlobalInitializer extends EmptyInBlockVisitor {

	BlockCodeGenerator cg;
	AdressGenerator a;
	 SymbolTable<String> tabulka;//tab. glob symbolov
	
	@Override
	public void visit(ResolvedDeclaration i) {//TODO aby sa nezabudlo pouzatvarat zatvorky
		String adr = a.next();
		cg.str.write(adr + "= global");
		tabulka.store(i.identifier, adr);
		String Typ=cg.getTypeString(i.type);
		cg.str.write(Typ);
		if(i.initializer == null){
			cg.str.writeLine("zeroinitializer");
		}else if(i.initializer instanceof ExpressionInitializer ){
			ExpressionInitializer j = (ExpressionInitializer) i.initializer;
			String exp = cg.getExpressionRegister(j.exp);
			cg.str.write(exp);
		}
		else if(i.initializer instanceof CompoundInitializer)
		{
			CompoundInitializer k= (CompoundInitializer) i.initializer;
			CodeGenInitializerVisitor iv = new CodeGenInitializerVisitor(cg);
			k.accept(iv);
			
		}
	}

}
