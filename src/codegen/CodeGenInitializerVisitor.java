package codegen;

import types.StructType;
import types.Type;
import declaration.initializer.CompoundInitializer;
import declaration.initializer.DesignatedInitializer;
import declaration.initializer.ExpressionInitializer;
import declaration.initializer.InitializerVisitor;

public class CodeGenInitializerVisitor implements InitializerVisitor {

	private BlockCodeGenerator cg;
	StructType i_typ;

	public CodeGenInitializerVisitor(BlockCodeGenerator cg,StructType t) {
		// TODO Auto-generated constructor stub
		this.cg=cg;
		this.i_typ=t;
	}

	@Override
	public void visit(ExpressionInitializer expressionInitializer) {
		// TODO Auto-generated method stub
		//sem sa chodit nebude

	}

	@Override
	public void visit(CompoundInitializer compoundInitializer) {
		// TODO Auto-generated method stub
		boolean neprvy=false;
		//Type x=i_typ;
		StructIteratorTypeVisitor stv = new StructIteratorTypeVisitor();
		i_typ.accept(stv);
		for(DesignatedInitializer  s: compoundInitializer.initializers ){//cyklus po jednotlivych initializeroch
			if(neprvy) cg.str.write(",");
			Type y=stv.iter();
			String Typ = cg.getTypeString(y);//cg.getExpressionTypeStr(s.designator.expr);
			cg.str.write(Typ);
			if(s.initializer == null){
				cg.str.write(" zeroinitializer ");
				/*CodeGenInitializerVisitor iv = new CodeGenInitializerVisitor(cg);
				s.initializer.accept(iv);*/
			}else if(s.initializer instanceof ExpressionInitializer){
				ExpressionInitializer e = (ExpressionInitializer) s.initializer;
				String Konst = cg.getExpressionRegister(e.exp);
				cg.str.write(Konst);
			}else if(s.initializer instanceof CompoundInitializer){
				cg.str.write("{");
				StructType x=(StructType) y;
				CodeGenInitializerVisitor iv = new CodeGenInitializerVisitor(cg,x);
				s.initializer.accept(iv);
				cg.str.write("}");
			}
			
		neprvy=true;	
		}
			

	}

}
