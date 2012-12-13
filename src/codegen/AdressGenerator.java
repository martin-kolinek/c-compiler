package codegen;

public class AdressGenerator {
	public int i;
	public String prefix;
	
	public AdressGenerator(String s){
		this.prefix=s;
	}
	
	public String next(){
		i++;
		return prefix + i;
	}
	
	public String akt(){
		return prefix + i;
	}

}
