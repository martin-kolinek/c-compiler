package declaration.declarator;

import java.util.ArrayList;

import declaration.specifiers.TypeQualifier;

public class PointerUtil {
	PointerDeclarator lastPtr = null;
	Declarator ret=null;
	public void addPointer(ArrayList<TypeQualifier> quals){
		if(ret==null) {
			ret=lastPtr=new PointerDeclarator(quals);
		} 
		else {
			PointerDeclarator tmp = new PointerDeclarator(quals);
			lastPtr.declarator = tmp;
			lastPtr=tmp;
		}
	}
	public Declarator getDecl(Declarator decl){
		if(lastPtr==null)
			ret=decl;
		else
			lastPtr.declarator = decl;
		return ret;
	}
	
}
