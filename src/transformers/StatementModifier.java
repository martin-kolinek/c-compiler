package transformers;

import statements.Statement;
import statements.StatementVisitor;

public interface StatementModifier extends StatementVisitor {
	Statement getResult();
}
