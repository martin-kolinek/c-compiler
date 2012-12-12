package transformers;

import java.util.List;

import toplevel.InBlock;
import toplevel.InBlockVisitor;

//Tieto by nemali lozit do vnutra bloku a volat rekurzivne seba
public interface BlockModifier extends InBlockVisitor {
	List<InBlock> getModified();
}
