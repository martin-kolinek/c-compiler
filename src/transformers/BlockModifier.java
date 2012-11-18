package transformers;

import java.util.List;

import toplevel.InBlock;
import toplevel.InBlockVisitor;

public interface BlockModifier extends InBlockVisitor {
	List<InBlock> getModified();
}
