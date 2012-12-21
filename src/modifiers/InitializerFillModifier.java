package modifiers;

import java.util.ArrayList;
import java.util.HashMap;

import astnode.ASTNode;
import declaration.ResolvedDeclaration;
import declaration.initializer.CompoundInitializer;
import declaration.initializer.DesignatedInitializer;
import declaration.initializer.Designator;
import declaration.initializer.ExpressionInitializer;
import declaration.initializer.Initializer;
import exceptions.SemanticException;
import expression.constant.IntConstantExpression;
import transformers.EmptyBlockModifier;
import types.ArrayType;
import types.StructType;
import types.Type;

/**
 * 
 * Na vrchnej urovni inicializera zoradi indexy a vyriesi nullove designatory
 * Zatial nevie ist na nizsiu uroven
 * Treba otestovat, bol by to zazrak, keby to fungovalo
 * 
 * @author GREPY
 *
 */
public class InitializerFillModifier extends EmptyBlockModifier {
	@Override
	public void visit(ResolvedDeclaration i) {
		i.initializer = fillSortInitializer(i.initializer, i.type, i);
		
		result.add(i);
	}
	
	private Initializer createCompoundForStruct(CompoundInitializer ci, StructType typ, ASTNode pos) {
		ArrayList<DesignatedInitializer> dis = new ArrayList<DesignatedInitializer>();
		HashMap<String, Initializer> original = new HashMap<String, Initializer>();
		ArrayList<Initializer> undesignated = new ArrayList<Initializer>();
		for(DesignatedInitializer di: ci.initializers) {
			if(di.designator==null)
				undesignated.add(di.initializer);
			if(di.designator.expr!=null)
				throw new SemanticException("Array designator in struct initialization", pos);
			assert di.designator.id!=null;
			original.put(di.designator.id, di.initializer);
		}
		
		int undesignatedIndex = 0;
		for(ResolvedDeclaration member:((StructType)typ).members) {
			Designator desig = new Designator(member.identifier);
			Initializer initial = null;
			if(original.containsKey(member.identifier)) {
				initial = original.get(member.identifier); 
				
			}
			else if(undesignated.size()>undesignatedIndex) {
				initial = undesignated.get(undesignatedIndex++);
			}
			initial = fillSortInitializer(initial, member.type, pos);
			dis.add(new DesignatedInitializer(desig, initial));
		}
		CompoundInitializer ret = new CompoundInitializer();
		ret.initializers=dis;
		return ret;
	}
	
	private Initializer createCompoundForArray(CompoundInitializer ci, ArrayType typ, ASTNode pos) {
		ArrayList<DesignatedInitializer> dis = new ArrayList<DesignatedInitializer>();
		ArrayList<Initializer> undesignated = new ArrayList<Initializer>();
		HashMap<Long, Initializer> original = new HashMap<Long, Initializer>();
		long size = 0;
		for(DesignatedInitializer di:ci.initializers) {
			if(di.designator!=null) {
				if(di.designator.id!=null)
					throw new SemanticException("Struct designator for array", pos);
				if(!(di.designator.expr instanceof IntConstantExpression))
					throw new SemanticException("Array designator must be a constant int expression", pos);
				long val = ((IntConstantExpression)di.designator.expr).value;
				if(val>size)
					size=val+1;
				original.put(val, di.initializer);
			}
			else {
				undesignated.add(di.initializer);
				size++;
			}
		}
		if(typ.size!=null) {
			size = ((IntConstantExpression)typ.size).value; 
		}
		int undesIndex = 0;
		for(int i=0; i<size; i++) {
			Designator desig = new Designator(new IntConstantExpression(i));
			Initializer init = null;
			if(original.containsKey(i)) {
				init = original.get(i);
			}
			else if(undesignated.size()>undesIndex) {
				init = undesignated.get(undesIndex++);
			}
			init = fillSortInitializer(init, typ.elementType, pos);
			dis.add(i, new DesignatedInitializer(desig, init));
		}
		
		CompoundInitializer ret = new CompoundInitializer();
		ret.initializers=dis;
		return ret;
	}
	
	private Initializer fillSortInitializer(Initializer init, Type typ, ASTNode pos){
		if (init == null){
			return init;
		} else if (init instanceof ExpressionInitializer){
			return init;
		} else {
			CompoundInitializer ci = (CompoundInitializer)init;
			if (typ instanceof StructType){
				return createCompoundForStruct(ci, (StructType)typ, pos);
			} 
			else if (typ instanceof ArrayType){
				ArrayType t = (ArrayType)typ;
				if(t.size == null || t.size instanceof IntConstantExpression)
					return createCompoundForArray(ci, t, pos);
				return init;
			}
			else
				throw new SemanticException("Compound initializer for non comound type", pos);
		}
	}
}
		