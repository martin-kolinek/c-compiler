package types;

import declaration.specifiers.Enumerator;
import expression.Expression;
import expression.binop.BinaryExpression;
import expression.binop.BinaryOperator;
import expression.constant.IntConstantExpression;
import symbols.SymbolTable;
import transformers.EmptyTypeModifier;

public class EnumRemoverTypeMod extends EmptyTypeModifier {
	
	private SymbolTable<Expression> mapping;
	
	public EnumRemoverTypeMod(SymbolTable<Expression> mapping) {
		this.mapping = mapping;
	}
	
	@Override
	public void visit(EnumType t) {
		Expression last = new IntConstantExpression(-1);
		if(t.enumerators!=null) {
			for(Enumerator e:t.enumerators) {
				if(e.expression!=null) {
					mapping.store(e.name, e.expression);
					last = e.expression;
				}
				else
					mapping.store(e.name, new BinaryExpression(last, BinaryOperator.PLUS, new IntConstantExpression(1)));
			}
		}
		result = PrimitiveType.INT;
	}
}
