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
}
