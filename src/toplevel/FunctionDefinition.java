package toplevel;

import java.util.ArrayList;

import declaration.Declaration;
import statements.Statement;
import types.Type;

public class FunctionDefinition implements InBlock {

	public Statement body;
	
	public Declaration declaration;
	
	public Type returnType;
	
	public ArrayList<FunctionParameter> parameters;
	
	public String name;
	
	@Override
	public void accept(InBlockVisitor v) {
		v.visit(this);
	}

}