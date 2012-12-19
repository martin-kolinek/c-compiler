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
		
		for(DesignatedInitializer  s: compoundInitializer.initializers ){
			String Typ = cg.getExpressionTypeStr(s.designator.expr);
			cg.str.write(Typ);
			if(s.initializer == null){
				CodeGenInitializerVisitor iv = new CodeGenInitializerVisitor(cg);
				s.initializer.accept(iv);
			}else if(s.initializer instanceof ExpressionInitializer){
				ExpressionInitializer e = (ExpressionInitializer) s.initializer;
				String Konst = cg.getExpressionRegister(e.exp);
				cg.str.write(Konst);
			}
			
			
		}
			

	}

}
