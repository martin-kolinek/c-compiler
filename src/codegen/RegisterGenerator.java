package codegen;

public class RegisterGenerator {
	private int i; //pocitadlo kvoli unikatnosti
	
	public RegisterGenerator(){
		this.i=0;
	}
	
	public String next(){
		i++;
		return "%" +Integer.toString(i);
	}
	
	public String akt(){
		return "%" +Integer.toString(i);
	}

}
