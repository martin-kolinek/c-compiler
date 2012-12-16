package position;

import java.util.HashMap;

import org.antlr.runtime.Token;
import astnode.ASTNode;

public class PositionTracker {
	
	private HashMap<ASTNode, Position> positions;
	
	public PositionTracker(){
		positions = new HashMap<ASTNode, Position>();
	}
	
	public void setPosition(ASTNode n, Token t) {
		positions.put(n, new Position(t.getLine(), t.getCharPositionInLine()));
	}
	
	public void setPosition(ASTNode n, ASTNode positionNode) {
		positions.put(n, positions.get(positionNode));
	}
	
	public Position getPosition(ASTNode n) {
		return positions.get(n);
	}
}
