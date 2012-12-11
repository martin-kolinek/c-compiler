package codegen;

public class Label {
	private String prefix;
	private int i; //pocitadlo kvoli unikatnosti
	char od='a';	//oddelovac cisel
	
	public Label(String s,int j){
		this.prefix=s + od + Integer.toString(j);
		this.i=0;
	}
	
	public String next(){
		i++;
		return prefix + od + i;
	}

}
