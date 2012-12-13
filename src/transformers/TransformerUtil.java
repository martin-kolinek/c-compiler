package transformers;

import declaration.initializer.Initializer;
import statements.Statement;
import toplevel.FunctionDefinition;
import toplevel.Program;
import types.Type;
import expression.Expression;

public class TransformerUtil {
	
	public static Expression transformExpression(Expression exp, ExpressionModifierFactory fac) {
		ExpressionTransformer trans = new ExpressionTransformer(fac);
		exp.accept(trans);
		ExpressionModifier mod = fac.create();
		exp.accept(mod);
		return mod.getResult();
	}
	
	public static Statement transformStatement(Statement st, StatementModifierFactory fac) {
		StatementTransformer trans = new StatementTransformer(fac);
		st.accept(trans);
		StatementModifier mod = fac.create();
		st.accept(mod);
		return mod.getResult();
	}
	
	public static Type transformType(Type t, TypeModifierFactory fac) {
		TypeTransformer trans = new TypeTransformer(fac);
		t.accept(trans);
		TypeModifier mod = fac.create();
		t.accept(mod);
		return mod.getResult();
	}
	
	public static Initializer transformInitializer(Initializer i, ExpressionModifierFactory fac) {
		InitializerTransformer trans = new InitializerTransformer(fac);
		i.accept(trans);
		return i;
	}
	
	public static void transformProgram(Program p, BlockModifierFactory fac) {
		BlockTransformer trans = new BlockTransformer(fac);
		p.declarations.accept(trans);
	}
	
	public static void transformProgram(Program p, final StatementModifierFactory fac) {
		transformProgram(p, new BlockModifierFactory() {
			
			@Override
			public void popModifierStack() {
			}
			
			@Override
			public BlockModifier createModifier(FunctionDefinition def) {
				return new StatementBlockModifier(fac);
			}
		});
	}
	
	public static void transformProgram(Program p, final ExpressionModifierFactory fac) {
		transformProgram(p, new BlockModifierFactory() {
			
			@Override
			public void popModifierStack() {
			}
			
			@Override
			public BlockModifier createModifier(FunctionDefinition def) {
				return new ExpressionBlockModifier(fac);
			}
		});
	}
	
	public static void transformProgram(Program p, final TypeModifierFactory fac) {
		transformProgram(p, new BlockModifierFactory() {
			
			@Override
			public void popModifierStack() {
			}
			
			@Override
			public BlockModifier createModifier(FunctionDefinition def) {
				return new TypeBlockModifier(fac);
			}
		});
	}
	
}
