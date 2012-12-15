package position;

import java.util.HashMap;

import antlr.Token;
import astnode.ASTNode;

public class PositionTracker {
	
  private HashMap<ASTNode, Token> NodeTokens;
  private HashMap<ASTNode, ASTNode> NodeNodes;
  
  public PositionTracker(){
	  NodeTokens = new HashMap<ASTNode, Token>();
	  NodeNodes = new HashMap<ASTNode, ASTNode>();
  }
	
  public void setPosition(ASTNode n, Token t) {
	  NodeTokens.put(n, t);
  }
  
  
  public void setPosition(ASTNode n, ASTNode positionNode) {
	  NodeNodes.put(n, positionNode);
  }
  
  public Position getPosition(ASTNode n) {
	  while (true){
		  if(NodeTokens.containsKey(n)){
			  Token token = NodeTokens.get(n);
			  return new Position(token.getLine(), token.getColumn());
		  } else {
			  if(NodeNodes.containsKey(n)){
				  n = NodeNodes.get(n);
			  } else {
				  return null;
			  }
		  }
	  }
  }
  
}