package toplevel;

import java.util.ArrayList;

import declaration.Declaration;
import statements.BlockStatement;
import types.Type;

public class FunctionDefinition implements InBlock {

	public BlockStatement body;
	
	public Declaration declaration;
	
	public Type returnType;
	
	public ArrayList<FunctionParameter> parameters;
	
	public String name;
	
	@Override
	public void accept(InBlockVisitor v) {
		v.visit(this);
	}

}
