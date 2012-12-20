package declaration.initializer;

public class DesignatedInitializer {
	public DesignatedInitializer() {
		
	}
	public DesignatedInitializer(Designator desig, Initializer init) {
		designator = desig;
		initializer = init;
	}
	public Designator designator;
	public Initializer initializer;
}
