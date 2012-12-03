package declaration;

import java.util.ArrayList;

import exceptions.SemanticException;
import types.IntType;
import types.PrimitiveTypeSpecifier;
import types.StorageClassSpecifier;
import types.Type;
import types.TypedefType;

public class DeclarationSpecifierParser {
	public DeclarationSpecifierParser() {
		scsSpecified = false;
		scs = StorageClassSpecifier.AUTO;
		constant = false;
	}
	
	private StorageClassSpecifier scs;
	public StorageClassSpecifier getScs() {
		return scs;
	}
	
	private boolean constant;

	public boolean isConstant() {
		return constant;
	}

	private boolean scsSpecified;
	
	public void addStorageClassSpecifier(StorageClassSpecifier scs) {
		if(scsSpecified)
			throw new SemanticException("storage class already specified");
		this.scs=scs;
	}
	
	public void addTypeQualifier(boolean constant) {
		this.constant |=constant;
	}
	
	private ArrayList<PrimitiveTypeSpecifier> pss;
	
	public void addPrimitiveTypeSpecifier(PrimitiveTypeSpecifier ps){
		pss.add(ps);
	}
	
	private String id;
	
	public void addIdentifier(String id){
		this.id=id;
	}
	
	public Type createType(){
		//sem treba pridat vytvaranie typov podla pridanych specifikatorov a kvalifikatorov
		if(id!=null) 
			return new TypedefType(id);
		return new IntType();
	}
}
