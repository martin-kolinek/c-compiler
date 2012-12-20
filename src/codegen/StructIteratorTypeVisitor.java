package codegen;

import java.util.Iterator;
import java.util.List;

import declaration.ResolvedDeclaration;
import types.ArrayType;
import types.EnumType;
import types.PointerType;
import types.PrimitiveType;
import types.StructType;
import types.Type;
import types.TypeVisitor;
import types.TypedefType;

public class StructIteratorTypeVisitor implements TypeVisitor {
	
	public List<ResolvedDeclaration> members;
	Iterator<ResolvedDeclaration> it;
	
	@Override
	public void visit(StructType t) {
		// TODO Auto-generated method stub
		this.members=t.members;
		it=this.members.iterator();

	}

	@Override
	public void visit(ArrayType t) {//nic
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TypedefType t) {//nic
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PrimitiveType t) {//nic
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(EnumType t) {//nic
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PointerType t) {//nic
		// TODO Auto-generated method stub

	}
	

	public Type iter() {
		// TODO Auto-generated method stub
		return it.next().type;
	}

}
