package types;

import declaration.Declaration;
import declaration.ResolvedDeclaration;
import expression.ConstVisitor;
import statements.Statement;
import toplevel.FunctionDefinition;
import toplevel.InBlockVisitor;

/**
 * Should only be accepted by types
 *
 */
public class ConstTypeSizeVisitor implements TypeVisitor, InBlockVisitor {

	private ExpressionTypeMapping etypes;
	
	public ConstTypeSizeVisitor(ExpressionTypeMapping mp){
		constant = true;
		etypes = mp;
	}
	
	private boolean constant;
	
	public boolean isConstant() {
		return constant;
	}

	@Override
	public void visit(Statement statement) {
		assert false;
	}

	@Override
	public void visit(Declaration declaration) {
		assert false;
	}

	@Override
	public void visit(FunctionDefinition functionDefinition) {
		assert false;
	}

	@Override
	public void visit(StructType structType) {
		for(ResolvedDeclaration d : structType.members) {
			d.type.accept((TypeVisitor)this);
		}
	}

	@Override
	public void visit(ArrayType arrayType) {
		ConstVisitor cv = new ConstVisitor(etypes);
		arrayType.getSizeExpression().accept(cv);
		if(!cv.expIsConstant())
			constant=false;
		arrayType.getElementType().accept((TypeVisitor)this);
	}

	@Override
	public void visit(TypedefType typedefType) {
		assert false; //these should have been expanded already
	}

	@Override
	public void visit(PrimitiveType primitiveType) {
		//do nothing
	}

	@Override
	public void visit(EnumType enumType) {
		//do nothing
	}

	@Override
	public void visit(Type type) {
		type.accept((TypeVisitor)this);
	}

	@Override
	public void visit(PointerType pointerType) {
		//do nothing
	}

}
