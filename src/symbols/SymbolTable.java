package symbols;

import java.util.HashMap;

public class SymbolTable<T> {
	private SymbolTable<T> par;
	
	private HashMap<String, T> map;
	
	public SymbolTable() {
		par=null;
		map = new HashMap<String, T>();
	}
	
	public SymbolTable(SymbolTable<T> parent){
		par=parent;
		map = new HashMap<String, T>();
	}
	
	public void store(String key, T value) {
		map.put(key, value);
	}
	
	public T get(String key){
		if(map.containsKey(key))
			return map.get(key);
		if(par==null)
			return null;
		return par.get(key);
	}
}
