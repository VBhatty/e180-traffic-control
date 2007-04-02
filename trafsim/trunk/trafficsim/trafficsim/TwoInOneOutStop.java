package trafficsim;

import java.util.Iterator;
import java.util.Queue;
import java.util.UUID;

public class TwoInOneOutStop extends trafficController{
	TwoInOneOutStop (){
		super();
		id = UUID.randomUUID();
		this.setSpeedlimit(0);
	}
	void updateTrafCont(double dt){
		if (!this.getVehicles().isEmpty()){
			Queue<Vehicle> veh = this.getVehicles();
			Iterator next = veh.iterator();
			Vehicle v = (Vehicle)next.next();
			
			//add the vehicle to the next road
			veh.remove();
			//find the road this vehicle came from to put it on correct internalNode
			Road lastRoad = v.getRoute().get((v.getRoutePos()-1));
			//find the itnernalNode which corresponds to last road
			InternalNode interNode = getInternalNode(lastRoad);
			interNode.addVehicle(v);
			updateInterns(dt);
		}
	}
	private void updateInterns(double dt) {
		interMap.updateNodes( dt);
		interMap.updateRoads( dt);
	}
}
