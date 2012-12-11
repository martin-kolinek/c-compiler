package codegen;

public class CodeGenerator {
	private int localCounter;
	public CodeGenerator()
	{
		localCounter=0;
	}
	
	public int getNextLocalNumber()
	{
		return localCounter++;
	}
	
	//<TODO
	public string vetvenie(Condition c,Block b1,Block b2)//neviem ako sa budu volat typy, toto je navrh struktury
	{
		generator.visit(c);//tu bude treba zapisat potrebny kod na vyhodnotenie podmienky a do c.premenna meno premennej s jej hodnotou  
		generator.visit(b1);//do b1.label treba label statemenu b1 
		generator.visit(b2);//podobne ako b2
		return "br " || c.premenna || " " || b1.label || " " || b2.label; //syntax celkom neviem ale takto nejak
	}
	//TODO>
}
