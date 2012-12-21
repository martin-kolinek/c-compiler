package codegen;

import exceptions.SemanticException;
import expression.constant.FloatConstantExpression;
import expression.constant.IntConstantExpression;
import astnode.ASTNode;
import types.Type;

import toplevel.EmptyInBlockVisitor;
import types.ArrayType;
import types.StructType;
import types.TypeClass;
import declaration.ResolvedDeclaration;
import declaration.initializer.CompoundInitializer;
import declaration.initializer.ExpressionInitializer;
import declaration.initializer.Initializer;

public class CodeGenGlobalInitializer extends EmptyInBlockVisitor {

	BlockCodeGenerator cg;
	
	public CodeGenGlobalInitializer(BlockCodeGenerator cg2) {
		this.cg=cg2;
	}

	@Override
	public void visit(ResolvedDeclaration i) {
		if(i.identifier==null)
			return;
		String adr = cg.getNextGlobalRegister();
		cg.str.write(adr + "= global ");
		cg.storeID(i.identifier, adr);
		if(TypeClass.isArray(i.type))
			cg.setGlobalArray(i.identifier, i.type);
		cg.str.write(getGlobalInitializer(i.type, i.initializer, i));
		cg.str.write("\n");
	}
	
	private String getGlobalInitializer(Type t, Initializer i, ASTNode pos) {
		if(i==null)
			return cg.getProperTypeString(t) + " zeroinitializer";
		if(i instanceof ExpressionInitializer) {
			if(!TypeClass.isArithmethic(t))
				throw new SemanticException("Expression initialization of non arithmethic type", pos);
			ExpressionInitializer ei = (ExpressionInitializer)i;
			if(!(ei.exp instanceof IntConstantExpression) && !(ei.exp instanceof FloatConstantExpression))
				throw new SemanticException("Initialization with a non constant expression on global scope", pos);
			return cg.getTypeString(t) + " " + cg.getExpressionRegister(ei.exp);
		}
		if(TypeClass.isStruct(t))
			return getGlobalInitializerStruct((StructType)t, (CompoundInitializer)i, pos);
		if(TypeClass.isArray(t))
			return getGlobalInitializerArray((ArrayType)t, (CompoundInitializer)i, pos);
		throw new SemanticException("Intalid initializer", pos);
	}
	
	private String getGlobalInitializerStruct(StructType st, CompoundInitializer init, ASTNode pos) {
		StringBuilder ret = new StringBuilder();
		ret.append(cg.getProperTypeString(st));
		ret.append(" ");
		ret.append("{");
		boolean first = true;
		for(int i=0; i<st.members.size(); i++) {
			if(!first) {
				ret.append(", ");
			}
			first = false;
			ret.append(getGlobalInitializer(st.members.get(i).type, init.initializers.get(i).initializer, pos));
		}
		ret.append("}");
		return ret.toString();
	}
	
	private String getGlobalInitializerArray(ArrayType at, CompoundInitializer init, ASTNode pos) {
		StringBuilder ret = new StringBuilder();
		ret.append(cg.getProperTypeString(at));
		ret.append(" ");
		ret.append("[");
		boolean first = true;
		if(!(at.size instanceof IntConstantExpression))
			throw new SemanticException("Array with non constant size in global scope", pos);//tu si myslim ze to osetrujeme skor ale nie som si isty a nemam cas to skusat
		long size = ((IntConstantExpression)at.size).value;
		if(size!=init.initializers.size())
			throw new SemanticException("Array size does not match the size of initializer", pos); //tu si myslim ze to osetrujeme skor ale nie som si isty a nemam cas to skusat
		for(int i=0; i<size; i++) {
			if(!first)
				ret.append(", ");
			first = false;
			ret.append(getGlobalInitializer(at.elementType, init.initializers.get(i).initializer, pos));
		}
		ret.append("]");
		return ret.toString();
	}

}
