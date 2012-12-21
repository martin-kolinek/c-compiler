package modifiers;

import java.util.ArrayList;

import declaration.ResolvedDeclaration;
import declaration.initializer.CompoundInitializer;
import declaration.initializer.DesignatedInitializer;
import declaration.initializer.ExpressionInitializer;
import declaration.initializer.Initializer;
import expression.AssignmentExpression;
import expression.Expression;
import expression.IDExpression;
import expression.IndexingExpression;
import expression.MemberAccessExpression;
import statements.OneexpressionStatement;
import statements.Statement;
import transformers.EmptyBlockModifier;

public class InitializerModifier extends EmptyBlockModifier {
	@Override
	public void visit(ResolvedDeclaration i) {
		ArrayList<Statement> stats = getStatements(i.initializer, new IDExpression(i.identifier));
		
		i.initializer = null;
		result.add(i);
		
		for (Statement statement : stats) {
			result.add(statement);
		}
	}
	
	private ArrayList<Statement> getStatements(Initializer init, Expression context){
		ArrayList<Statement> stats = new ArrayList<Statement>();
		if (init == null){
			//do nothing
		} else if (init instanceof ExpressionInitializer){
			//expression
			stats.add(new OneexpressionStatement(new AssignmentExpression(context, ((ExpressionInitializer)init).exp)));
		} else {
			//compound
			for (DesignatedInitializer di : ((CompoundInitializer)init).initializers){
				if (di.designator.expr != null){
					//indexing
					stats.addAll(getStatements(di.initializer, new IndexingExpression(context, di.designator.expr)));
				} else if (di.designator.id != null){
					//member
					stats.addAll(getStatements(di.initializer, new MemberAccessExpression(context, di.designator.id)));
				} else {
					assert false; //toto by nemalo nastat
					//default indexing
				}
			}
		}
		return stats;
	}
}
