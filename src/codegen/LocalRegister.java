package codegen;

public class LocalRegister implements IRValue {
	private int number;
	public LocalRegister(CodeGenerator gen)
	{
		number=gen.getNextLocalNumber();
	}
	@Override
	public String getStringRepresentation() {
		return "%"+number;
	}
}
