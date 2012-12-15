package codegen;

import typeresolve.ExpressionTypeMapping;

public class VisitPack {
	
	public VisitPack(CodeGenStream wr2, LabelGenerator l2,
			RegisterGenerator r2) {
		this.l=l2;
		this.r=r2;
		this.wr=wr2;
	}
	
	public LabelGenerator l;
	public CodeGenStream wr;
	public RegisterGenerator r;
	public ExpressionTypeMapping t;

}
