package trafficsim;

import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

/**
 * a trafficController node with 2 inRoads and 2 outRoads so that
 * turning is not possible and a stop sign in each direction
 
 * @author Eric Vacca
 *
 */
public class twoWayStop extends trafficController {
	Queue<Vehicle> inRoad1List;
	Queue<Vehicle> inRoad2List;
	twoWayStop (){
		super();
		id = UUID.randomUUID();
		this.setSpeedlimit(0);
	}
	public Queue<Vehicle> getVehicles() {
		return super.getVehicles();
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
		generateInNodeVehList();
		//let one car go in each direction if it is added to the node
		if (!this.inRoad1List.isEmpty() || !inRoad2List.isEmpty()){
			//get the next vehicle to let through to next road on route
			Iterator road1Iter = inRoad1List.iterator();
			Iterator road2Iter = inRoad2List.iterator();
			if (road1Iter.hasNext()){
				Vehicle v1 = (Vehicle)road1Iter.next();
				this.removeVehicle(v1);
				Road newRoad = v1.getRoute().get((v1.getRoutePos()));
				newRoad.addVehicle(v1);
			}
			if (road2Iter.hasNext()){
				Vehicle v2 = (Vehicle)road1Iter.next();
				this.removeVehicle(v2);
				Road newRoad = v2.getRoute().get((v2.getRoutePos()));
				newRoad.addVehicle(v2);
			}
		}
	}
	void generateInNodeVehList(){
		//should be a set of 2
		Set InEdges = getInEdges();
		Iterator iter= InEdges.iterator();	
		Road r1 = (Road) iter.next();
		Road r2 = (Road) iter.next();
		
		Iterator veh = getVehicles().iterator();
		while (veh.hasNext()){
			Vehicle v = (Vehicle)veh.next();
			this.removeVehicle(v);
			if(v.getRoute().get(v.getRoutePos()-1).equals(r1)){
				inRoad1List.add(v);
			}
			if(v.getRoute().get(v.getRoutePos()-1).equals(r2)){
				inRoad2List.add(v);
			}
		}
	}
}
