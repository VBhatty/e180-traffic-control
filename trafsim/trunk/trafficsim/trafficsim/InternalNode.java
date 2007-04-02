package trafficsim;

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
}
