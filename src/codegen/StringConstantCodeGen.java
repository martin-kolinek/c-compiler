package codegen;

import java.util.LinkedHashMap;

public class StringConstantCodeGen {
	BlockCodeGenerator cg;
	LinkedHashMap<byte[], String> consts;
	public StringConstantCodeGen(BlockCodeGenerator cg) {
		this.cg=cg;
		consts = new LinkedHashMap<byte[], String>();
	}
	
	public String addStringConstant(byte[] value) {
		if(consts.containsKey(value))
			return consts.get(value);
		String reg = cg.getNextGlobalRegister();
		consts.put(value, reg);		
		return reg;
	}
	
	private String getByteString(byte[] bs) {
		StringBuilder sb = new StringBuilder();
		for(byte b : bs) {
			sb.append(String.format("%x", b));
		}
		return sb.toString();
	}
	
	public void writeOutput(){
		for(byte[] value:consts.keySet()) {
			String reg = consts.get(value);
			cg.str.writeAssignment(reg, "private unnamed_addr constant [", Integer.toString(value.length), "x i8 ]", "c\""+getByteString(value)+"\"",", align 1");
		}
	}
}
