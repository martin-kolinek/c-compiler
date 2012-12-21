package codegen;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class CodeGenStream {
	private OutputStreamWriter sw;
	public CodeGenStream(OutputStreamWriter sw) {
		this.sw=sw;
	}
	
	public void writeLine(String... ss) {
		try {
			for(String s : ss) {
				sw.append(s);
				sw.append(" ");
			}
			sw.append("\n");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeLabel(String l) {
		writeLine("br label", l);
		l=l.substring(1);
		writeLine(l+":");
	}
	
	public void writeAssignment(String result, String... ss) {
		ArrayList<String> arr = new ArrayList<String>();
		arr.add(result);
		arr.add("=");
		for(String s:ss)
			arr.add(s);
		writeLine(arr.toArray(ss));
	}

	public void store(String adress, String typ, String register) {
		this.writeLine("store",typ,register,",",typ,"*",adress);
		// TODO Auto-generated method stub
		
	}
	
	public void write(String s){
		try {
			sw.append(" " + s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
