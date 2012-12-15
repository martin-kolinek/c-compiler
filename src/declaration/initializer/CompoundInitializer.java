package declaration.initializer;

import java.util.ArrayList;

import astnode.ASTNode;

public class CompoundInitializer implements Initializer, ASTNode {

	public CompoundInitializer() {
		initializers=new ArrayList<DesignatedInitializer>();
	}
	
	public ArrayList<DesignatedInitializer> initializers;
	
	@Override
	public void accept(InitializerVisitor v) {
		v.visit(this);
	}

}
