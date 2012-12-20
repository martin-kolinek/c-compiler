package modifiers;

import java.util.ArrayList;
import java.util.HashMap;

import declaration.ResolvedDeclaration;
import declaration.initializer.CompoundInitializer;
import declaration.initializer.DesignatedInitializer;
import declaration.initializer.Designator;
import declaration.initializer.ExpressionInitializer;
import declaration.initializer.Initializer;
import expression.constant.IntConstantExpression;
import transformers.EmptyBlockModifier;
import types.ArrayType;
import types.StructType;
import types.Type;

/**
 * 
 * Na vrchnej urovni inicializera zoradi indexy a vyriesi nullove designatory
 * Zatial nevie ist na nizsiu uroven
 * Treba otestovat, bol by to zazrak, keby to fungovalo
 * 
 * @author GREPY
 *
 */
public class InitializerFillModifier extends EmptyBlockModifier {
	@Override
	public void visit(ResolvedDeclaration i) {
		i.initializer = fillSortInitializer(i.initializer, i.type);
		
		result.add(i);
	}
	
	private Initializer fillSortInitializer(Initializer init, Type typ){
		if (init == null){
			//do nothing
		} else if (init instanceof ExpressionInitializer){
			//one expression - do nothing
		} else {
			//compound
			//first, fill and sort all children initializers //NEVIEM
			ArrayList<DesignatedInitializer> dis = new ArrayList<DesignatedInitializer>();
			
			//then fill and sort this initializer
			if (typ instanceof StructType){
				//expect structure
				HashMap<String, Boolean> used_members = new HashMap<String, Boolean>(); 
				HashMap<Integer, String> member_nums = new HashMap<Integer, String>();
				HashMap<String, Integer> nums_member = new HashMap<String, Integer>(); 
				//No member used yet, compute number for each member
				int i = 0;
				for (ResolvedDeclaration member : ((StructType) typ).members){
					used_members.put(member.identifier, false);
					member_nums.put(i, member.identifier);
					nums_member.put(member.identifier, i);
					i++;
				}
				
				
				//create null initializers
				for (int j=0; j<i; j++){
					DesignatedInitializer di = new DesignatedInitializer();
					di.designator = new Designator(member_nums.get(j));
					di.initializer = null;
					dis.add(di);
				}
				
				//Iterate over initializers and insert into dis
				for (DesignatedInitializer di : ((CompoundInitializer)init).initializers){
					if (di.designator.id != null){
						dis.set(nums_member.get(di.designator.id), di);
						used_members.put(di.designator.id, true);
					}
				}
				
				//Not specified id's
				int ind = 0;
				for (DesignatedInitializer di : ((CompoundInitializer)init).initializers){
					if (di.designator.id == null){
						//find first next unused member
						while (used_members.get(member_nums.get(ind))){ 
							ind++;
						}
						di.designator.id = member_nums.get(ind); //set apropriate designator id
						dis.set(ind, di); //replace
						used_members.put(di.designator.id, true);
					}
				}
				
			} else if (typ instanceof ArrayType){
				//expect array
				int count = 0;
				Long highest = new Long(0);
				Boolean all_ints = true;
				for (DesignatedInitializer di : ((CompoundInitializer)init).initializers){
					if (di.designator.expr instanceof IntConstantExpression){
						if ((((IntConstantExpression)di.designator.expr).value) > highest)
							highest = ((IntConstantExpression)di.designator.expr).value;
					} else {
						all_ints = false;
					}
					count++;
				}
				
				if (all_ints){ //if all indexes are integers (or nulls), sort and fill
					
					Long max_index = Math.max(highest+1, count);
					
					//create null initializers
					for (int j=0; j<max_index; j++){
						DesignatedInitializer di = new DesignatedInitializer();
						di.designator = new Designator(new IntConstantExpression(j));
						di.initializer = null;
						dis.add(di);
					}
					
					//fill initializers
					for (DesignatedInitializer di : ((CompoundInitializer)init).initializers){
						if (di.designator.expr != null){
							dis.set((int) ((IntConstantExpression)di.designator.expr).value, di);
						}
					}
					
					//not specified keys
					for (DesignatedInitializer di : ((CompoundInitializer)init).initializers){
						if (di.designator.expr == null){
							//find first next null initializer
							int ind = 0;
							while (dis.get(ind).initializer != null){ 
								ind++;
							}
							di.designator.expr = new IntConstantExpression(ind); //set apropriate designator id
							dis.set(ind, di); //replace
						}
					}
					
				}
				
			}
			((CompoundInitializer)init).initializers = dis;
		}
		return init;
	}
}
