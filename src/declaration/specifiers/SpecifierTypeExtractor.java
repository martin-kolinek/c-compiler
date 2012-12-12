package declaration.specifiers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import declaration.Declaration;
import declaration.DeclarationResolver;
import declaration.ResolvedDeclaration;
import exceptions.SemanticException;

import types.EnumType;
import types.PrimitiveType;
import types.StructType;
import types.Type;
import types.TypedefType;

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
		extern = false;
		struct = null;
		enumeration = null;
		typedef = null;
		isTypedef = false;
		inline = false;
	}
	
	private ArrayList<PrimitiveTypeSpecifier> primitiveTypes;
	private HashMap<List<PrimitiveTypeSpecifier>, Type> typeMap;
	private boolean extern;
	private StructType struct;
	private EnumType enumeration;
	private TypedefType typedef;
	private boolean isTypedef;
	private boolean inline;
	
	public Type getType() {
		int types = 0;
		if(primitiveTypes.size()!=0)
			types++;
		if(struct!=null)
			types++;
		if(enumeration!=null)
			types++;
		if(typedef!=null)
			types++;
		if(types>1)
			throw new SemanticException("Multiple types defined"); 
		if(types==0)
			throw new SemanticException("No type defined");
		if(primitiveTypes.size()!=0){
			if(typeMap.containsKey(primitiveTypes)) {
				return typeMap.get(primitiveTypes);
			}
			throw new SemanticException("Not a known primitive type specifier combination");
		}
		if(struct!=null)
			return struct;
		if(enumeration!=null)
			return enumeration;
		if(typedef!=null)
			return typedef;
		assert false;
		return null;
	}
	
	public boolean isTypedef() {
		return isTypedef;
	}
	
	public boolean isExtern() {
		return extern;
	}
	
	public boolean isInline() {
		return inline;
	}
	
	@Override
	public void visit(PrimitiveTypeSpecifier primitiveTypeSpecifier) {
		primitiveTypes.add(primitiveTypeSpecifier);
	}

	@Override
	public void visit(TypeQualifier typeQualifier) {
		//we can ignore these
	}

	@Override
	public void visit(StorageClassSpecifier storageClassSpecifier) {
		if(storageClassSpecifier==StorageClassSpecifier.EXTERN)
			extern = true;
	}

	@Override
	public void visit(StructSpecifier structSpecifier) {
		ArrayList<ResolvedDeclaration> decls = null;
		if(structSpecifier.memberDecls!=null){
			decls = new ArrayList<ResolvedDeclaration>();
			for(Declaration d : structSpecifier.memberDecls) {
				DeclarationResolver res = new DeclarationResolver();
				d.accept(res);
				if(res.resultDecls.size()==0) {
					throw new SemanticException("Member declaration not valid");
				}
				decls.add(res.resultDecls.get(0));
			}
		}
		struct = new StructType(structSpecifier.tag, decls);
	}

	@Override
	public void visit(EnumSpecifier enumSpecifier) {
		enumeration = new EnumType(enumSpecifier.tag, enumSpecifier.enumerators);
	}

	@Override
	public void visit(IDDeclarationSpecifier idDeclarationSpecifier) {
		typedef = new TypedefType(idDeclarationSpecifier.id);
	}

	@Override
	public void visit(TypedefDeclarationSpecifier typedefDeclarationSpecifier) {
		isTypedef=true;
	}

	@Override
	public void visit(InlineDeclarationSpecifier inlineDeclarationSpecifier) {
		inline = true;
	}
	
	
	
}
