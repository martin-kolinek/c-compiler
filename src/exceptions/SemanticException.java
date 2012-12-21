package exceptions;

import position.Position;
import position.PositionTracker;
import astnode.ASTNode;

public class SemanticException extends RuntimeException {
	static final long serialVersionUID = 1;
	public final String message;
	private ASTNode nd;
	public SemanticException(String msg, ASTNode posnode) {
		message=msg;
		nd = posnode;
	}
	
	public String getMessage(PositionTracker pos) {
		Position p = pos.getPosition(nd);
		String pstr = "unknown position";
		if(p!=null)
			pstr = p.toString();
		return pstr+": "+message;
	}
}
