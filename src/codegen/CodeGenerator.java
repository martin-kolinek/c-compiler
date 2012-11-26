package codegen;

public class CodeGenerator {
	private int localCounter;
	public CodeGenerator()
	{
		localCounter=0;
	}
	
	public int getNextLocalNumber()
	{
		return localCounter++;
	}
}
