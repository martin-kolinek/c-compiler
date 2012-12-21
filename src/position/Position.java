package position;

public class Position {
	public int line;
	public int column;
	
	public Position(int lin, int col){
		line = lin;
		column = col;
	}
	
	@Override
	public String toString() {
		return Integer.toString(line)+":"+Integer.toString(column);
	}
}
