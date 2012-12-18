package codegen;

import types.ArrayType;
import types.EnumType;
import types.PointerType;
import types.PrimitiveType;
import types.StructType;
import types.TypeVisitor;
import types.TypedefType;

public class CodeGenTypeVisitor implements TypeVisitor {

	private VisitPack pack;
	private String Typ;
	boolean unsig = false;
	private boolean integer = false;

	public CodeGenTypeVisitor(VisitPack pack) {
		this.pack=pack;
	}

	@Override
	public void visit(StructType t) {
		
		
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ArrayType t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TypedefType t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PrimitiveType t) {
		
		if (!t.sign) unsig=true;
		if (!t.floating){
			integer=true;
			Typ = "i" + Integer.toString(t.size*8);
		}
		else{
			integer=false;
			switch(t.size){
			case 3 :
				Typ="float";
				break;
			case 4 :
				Typ="double";
				break;
			}			
		}
	}

	@Override
	public void visit(EnumType t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PointerType t) {
		// TODO Auto-generated method stub
		CodeGenTypeVisitor tv = new CodeGenTypeVisitor(pack);
		t.pointedToType.accept(tv);
		String Typ2 = tv.GetTypeText();
		Typ="*" +Typ2;
		integer=true;
		unsig = true;

	}

	public String GetTypeText() {
		// TODO Auto-generated method stub
		return this.Typ;
	}

	public boolean unsig() {
		// TODO Auto-generated method stub
		return unsig;
	}

	public boolean integer() {
		// TODO Auto-generated method stub
		return integer ;
	}

}
