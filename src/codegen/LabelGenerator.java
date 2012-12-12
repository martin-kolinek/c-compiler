package codegen;

public class LabelGenerator {
	private String prefix;
	private int i; //pocitadlo kvoli unikatnosti
	
	public LabelGenerator(String s){
		this.prefix=s;
		this.i=0;
	}
	
	public String next(){
		i++;
		return " label "+prefix + i;
	}

}


/*konvencia: ked sa vojde do niektoreho vrcholu s label hned sa ako prve zaciatok=label.next
 * nasledne sa hned pise label zaciatok do LLVM kodu
 * ako druhe sa vzplni koniec=label.next
 * 
 * potom sa pusti visitor LabelVisitor pre synovske vrcholy pricom 
 * */


