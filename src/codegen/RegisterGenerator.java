package codegen;

public class RegisterGenerator {
	private int i; //pocitadlo kvoli unikatnosti
	String prefix;
	public RegisterGenerator(String prefix){
		this.prefix=prefix;
		this.i=0;
	}
	
	public String next(){
		i++;
		return prefix +Integer.toString(i);
	}
}
