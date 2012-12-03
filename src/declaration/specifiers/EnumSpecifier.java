package declaration.specifiers;

import java.util.ArrayList;

public class EnumSpecifier implements DeclarationSpecifier {
	
	public EnumSpecifier(String tag) {
		enumerators = null;
		this.tag=tag;
	}
	
	public EnumSpecifier() {
		tag=null;
		enumerators = new ArrayList<Enumerator>();
	}
	
	public String tag;
	
	public ArrayList<Enumerator> enumerators;
	
	@Override
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);
	}

}
