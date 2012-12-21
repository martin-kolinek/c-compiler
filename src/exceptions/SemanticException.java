package exceptions;

import astnode.ASTNode;

public class SemanticException extends RuntimeException {
	static final long serialVersionUID = 1;
	public final String message; 
	public SemanticException(String msg, ASTNode posnode) {
		message=msg;
	}
}
