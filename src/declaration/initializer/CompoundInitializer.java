package declaration.initializer;

import java.util.ArrayList;

public class CompoundInitializer implements Initializer {

	public CompoundInitializer() {
		initializers=new ArrayList<DesignatedInitializer>();
	}
	
	public ArrayList<DesignatedInitializer> initializers;
	
	@Override
	public void accept(InitializerVisitor v) {
		v.visit(this);
	}

}
