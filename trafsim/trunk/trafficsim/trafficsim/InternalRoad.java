package trafficsim;

/**
 * Internal roads have no length but have a set amount
 * of cars which can be on it at one time.  It can only have
 * cars on it if it on field is true.  Otherwise the vehicles
 * stay at the InternalNode
 * @author Eric Vacca
 *
 */
public class InternalRoad extends Road{
	int carLimit;  //max cars allowed on this road
	
	boolean on;
	InternalRoad(InternalNode n1, InternalNode n2,int limit, boolean init) {
		super(n1, n2);
		carLimit=limit;
		on = init;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void addVehicle(Vehicle vehicle) {
		if (getVehicles().size()<carLimit){
			super.addVehicle(vehicle);
		}
	}
}
