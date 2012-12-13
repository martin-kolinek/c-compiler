package codegen;

public class AdressGenerator {
	public int i;
	public String prefix;// = @a
	
	public AdressGenerator(){
		this.prefix= "@a";
		i=0;
	}
	
	public String next(){
		i++;
		return prefix + i;
	}
	
	public String akt(){
		return prefix + i;
	}

}
