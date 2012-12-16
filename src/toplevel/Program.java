package toplevel;

import astnode.ASTNode;
import statements.BlockStatement;

public class Program implements ASTNode {
	public BlockStatement declarations;
	public Program(){
		declarations=new BlockStatement();
	}
}
