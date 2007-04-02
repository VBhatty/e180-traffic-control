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
			Road newRoad = v.getRoute().get((v.getRoutePos()));
			newRoad.addVehicle(v);
		}
	}
}
