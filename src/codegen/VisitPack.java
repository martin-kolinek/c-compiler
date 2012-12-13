package codegen;

import java.io.OutputStreamWriter;

import symbols.AdressTable;

public class VisitPack {

	
	public VisitPack(OutputStreamWriter wr2, LabelGenerator l2,
			RegisterGenerator r2, AdressTable t) {
		this.l=l2;
		this.r=r2;
		this.wr=wr2;
		this.table=t;
		
		// TODO Auto-generated constructor stub
	}
	public AdressTable table;
	public LabelGenerator l;
	public OutputStreamWriter wr;
	public RegisterGenerator r;

}
