package trafficsim;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * Node used by supernode.  Has reference to the road
 * which created it
 * @author Eric Vacca
 *
 */
public class InternalNode extends trafficController{
	Road myRef;
	InternalNode(Road myRefRoad){
		super();
		id = UUID.randomUUID();
		myRef = myRefRoad;
	}
	public Road getMyRef() {
		return myRef;
	}
	/**
	 * each internal has exactly one out edge.
	 * 
	 * @return
	 */
	InternalRoad getOutEgde(){
		Set out = this.getOutEdges();
		Iterator iter = out.iterator();
		return (InternalRoad)iter.next();
	}
	Vehicle getNextVehicle(){
		Vehicle V = new Nullvehicle();
		Iterator iter = getVehicles().iterator();
		if( iter.hasNext()){
			V= (Vehicle) iter.next();
			getVehicles().remove();
		}
		return V;
	}
}
