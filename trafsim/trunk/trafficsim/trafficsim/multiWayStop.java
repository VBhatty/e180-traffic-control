package trafficsim;

import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

/**
 * 
 * @author Eric Vacca
 *
 */
public class multiWayStop extends trafficController {
	private Queue<Vehicle> vehicles;
	
	public Queue<Vehicle> getVehicles() {
		return vehicles;
	}
	public void addVehicle(Vehicle v) {	
		vehicles.add(v);
	}
	boolean removeVehicle(Vehicle v){
		return vehicles.remove(v);
	}
	@Override
	public Set getInEdges() {
		// TODO Auto-generated method stub
		return super.getInEdges();
	}
	@Override
	public Set getOutEdges() {
		// TODO Auto-generated method stub
		return super.getOutEdges();
	}
	void updateTrafCont(double dt){
		if (!this.getVehicles().isEmpty()){
			//get the next vehicle to let through to next road on route
			Queue<Vehicle> veh = this.getVehicles();
			Iterator next = veh.iterator();
			Vehicle v = (Vehicle)next.next();
			
			//find any other vehicles that are either going 
			//add the vehicle to the next road
			veh.remove();
			Road newRoad = v.getRoute().get((v.getRoutePos()+1));
			newRoad.addVehicle(v);
		}
	}
}
