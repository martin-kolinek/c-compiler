package codegen;

public class Label {
	private String prefix;
	private int i; //pocitadlo kvoli unikatnosti
	char od='a';	//oddelovac cisel
	
	public Label(String s){
		this.prefix=s + od;
		this.i=0;
	}
	
	public String next(){
		i++;
		return prefix + od + i;
	}
	
	public String akt(){
		return prefix + od + i;
	}

}


/*konvencia: ked sa vojde do niektoreho vrcholu s label hned sa ako prve zaciatok=label.next
 * nasledne sa hned pise label zaciatok do LLVM kodu
 * ako druhe sa vzplni koniec=label.next
 * 
 * potom sa pusti visitor LabelVisitor pre synovske vrcholy pricom 
 * */


