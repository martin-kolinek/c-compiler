package codegen;

import declaration.initializer.CompoundInitializer;
import declaration.initializer.DesignatedInitializer;
import declaration.initializer.ExpressionInitializer;
import declaration.initializer.InitializerVisitor;

public class CodeGenInitializerVisitor implements InitializerVisitor {

	private BlockCodeGenerator cg;

	public CodeGenInitializerVisitor(BlockCodeGenerator cg) {
		// TODO Auto-generated constructor stub
		this.cg=cg;
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
		
		for(DesignatedInitializer  s: compoundInitializer.initializers ){//cyklus po jednotlivych initializeroch
			if(neprvy) cg.str.write(",");
			String Typ = cg.getExpressionTypeStr(s.designator.expr);
			cg.str.write(Typ);
			if(s.initializer == null){
				cg.str.write("zeroinitializer");
				/*CodeGenInitializerVisitor iv = new CodeGenInitializerVisitor(cg);
				s.initializer.accept(iv);*/
			}else if(s.initializer instanceof ExpressionInitializer){
				ExpressionInitializer e = (ExpressionInitializer) s.initializer;
				String Konst = cg.getExpressionRegister(e.exp);
				cg.str.write(Konst);
			}else if(s.initializer instanceof CompoundInitializer){
				cg.str.write("{");
				CodeGenInitializerVisitor iv = new CodeGenInitializerVisitor(cg);
				s.initializer.accept(iv);
				cg.str.write("}");
			}
			
		neprvy=true;	
		}
			

	}

}
