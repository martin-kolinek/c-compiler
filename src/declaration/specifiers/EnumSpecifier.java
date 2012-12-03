package declaration.specifiers;

import java.util.ArrayList;

public class EnumSpecifier implements DeclarationSpecifier {
	
	public EnumSpecifier(String tag) {
		enumerators = new ArrayList<Enumerator>();
		this.tag=tag;
	}
	
	public String tag;
	
	public ArrayList<Enumerator> enumerators;
	
	@Override
	public void accept(DeclarationSpecifierVisitor v) {
		v.visit(this);
	}

}
