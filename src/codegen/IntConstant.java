package codegen;

public class IntConstant implements IRValue {

	private int i;
	public IntConstant(int i)
	{
		this.i=i;
	}
	@Override
	public String getStringRepresentation() {
		return Integer.toString(i);
	}

}
