package declaration.specifiers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import types.PrimitiveType;
import types.Type;

public class SpecifierTypeExtractor implements DeclarationSpecifierVisitor {

	public SpecifierTypeExtractor(){
		typeMap = new HashMap<List<PrimitiveTypeSpecifier>, Type>();
		primitiveTypes = new ArrayList<PrimitiveTypeSpecifier>();
		//void
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.VOID), PrimitiveType.VOID);
		//char
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.CHAR), PrimitiveType.CHAR);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.SIGNED, PrimitiveTypeSpecifier.CHAR), PrimitiveType.CHAR);
		//unsigned char
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.UNSIGNED, PrimitiveTypeSpecifier.CHAR), PrimitiveType.UCHAR);
		//short
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.SHORT), PrimitiveType.SHORT);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.SIGNED, PrimitiveTypeSpecifier.SHORT), PrimitiveType.SHORT);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.SHORT, PrimitiveTypeSpecifier.INT), PrimitiveType.SHORT);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.SIGNED, PrimitiveTypeSpecifier.SHORT, PrimitiveTypeSpecifier.INT), PrimitiveType.SHORT);
		//unsigned short
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.UNSIGNED, PrimitiveTypeSpecifier.SHORT), PrimitiveType.USHORT);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.UNSIGNED, PrimitiveTypeSpecifier.SHORT, PrimitiveTypeSpecifier.INT), PrimitiveType.USHORT);
		//int
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.INT), PrimitiveType.INT);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.SIGNED), PrimitiveType.INT);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.SIGNED, PrimitiveTypeSpecifier.INT), PrimitiveType.INT);
		//unsigned int
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.UNSIGNED), PrimitiveType.INT);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.UNSIGNED, PrimitiveTypeSpecifier.INT), PrimitiveType.INT);
		//long
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.LONG), PrimitiveType.LONG);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.SIGNED, PrimitiveTypeSpecifier.LONG), PrimitiveType.LONG);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.LONG, PrimitiveTypeSpecifier.INT), PrimitiveType.LONG);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.SIGNED, PrimitiveTypeSpecifier.LONG, PrimitiveTypeSpecifier.INT), PrimitiveType.LONG);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.LONG, PrimitiveTypeSpecifier.LONG), PrimitiveType.SHORT);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.SIGNED, PrimitiveTypeSpecifier.LONG, PrimitiveTypeSpecifier.LONG), PrimitiveType.LONG);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.LONG, PrimitiveTypeSpecifier.LONG, PrimitiveTypeSpecifier.INT), PrimitiveType.LONG);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.SIGNED, PrimitiveTypeSpecifier.LONG, PrimitiveTypeSpecifier.LONG, PrimitiveTypeSpecifier.INT), PrimitiveType.LONG);
		//unsigned long
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.UNSIGNED, PrimitiveTypeSpecifier.LONG), PrimitiveType.ULONG);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.UNSIGNED, PrimitiveTypeSpecifier.LONG, PrimitiveTypeSpecifier.INT), PrimitiveType.ULONG);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.UNSIGNED, PrimitiveTypeSpecifier.LONG, PrimitiveTypeSpecifier.LONG), PrimitiveType.ULONG);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.UNSIGNED, PrimitiveTypeSpecifier.LONG, PrimitiveTypeSpecifier.LONG, PrimitiveTypeSpecifier.INT), PrimitiveType.ULONG);
		//float
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.FLOAT), PrimitiveType.FLOAT);
		//double
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.DOUBLE), PrimitiveType.FLOAT);
		typeMap.put(Arrays.asList(PrimitiveTypeSpecifier.LONG, PrimitiveTypeSpecifier.DOUBLE), PrimitiveType.FLOAT);
	}
	
	private ArrayList<PrimitiveTypeSpecifier> primitiveTypes;
	private HashMap<List<PrimitiveTypeSpecifier>, Type> typeMap;
	
	@Override
	public void visit(PrimitiveTypeSpecifier primitiveTypeSpecifier) {
		primitiveTypes.add(primitiveTypeSpecifier);
	}

	@Override
	public void visit(TypeQualifier typeQualifier) {
		
	}

	@Override
	public void visit(StorageClassSpecifier storageClassSpecifier) {
		
	}

	@Override
	public void visit(StructSpecifier structSpecifier) {
		
	}

	@Override
	public void visit(EnumSpecifier enumSpecifier) {
		
	}

	@Override
	public void visit(IDDeclarationSpecifier idDeclarationSpecifier) {
		
	}

	@Override
	public void visit(TypedefDeclarationSpecifier typedefDeclarationSpecifier) {
		
	}

	@Override
	public void visit(InlineDeclarationSpecifier inlineDeclarationSpecifier) {
		
	}
	
}
