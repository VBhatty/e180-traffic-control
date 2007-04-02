package trafficsim;

import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

public class oneRoadStopLight extends trafficController {
	private Queue<Vehicle> vehicles;
	boolean light = false; //true means light is green, false is red
	double greenTimer;
	double redTimer;
	double timeSinceChange=0;
	oneRoadStopLight (){
		super();
		id = UUID.randomUUID();
	}
	void updateTrafCont(double dt){
		updateLight(dt);
		if (!this.getVehicles().isEmpty() && light){
			//calculate how many vehicles to let through
			Queue<Vehicle> veh = this.getVehicles();
			Iterator next = veh.iterator();
			Vehicle v = (Vehicle)next.next();
			
			//add the vehicle to the next road
			veh.remove();
			Road newRoad = v.getRoute().get((v.getRoutePos()));
			newRoad.addVehicle(v);
		}
	}
	boolean updateLight(double dt){
		timeSinceChange = timeSinceChange+dt;
		Set edges = this.getOutEdges();
		Iterator iter = edges.iterator();
		Road r = (Road)iter.next();
		if (light && timeSinceChange>=greenTimer){
			 light = false;
			 setSpeedlimit(r.getSpeedLimit());
		}
		else if (!light && timeSinceChange>=redTimer){
			light = true; 
			setSpeedlimit(0);
		}
		return light;
	}
	public Queue<Vehicle> getVehicles() {
		return vehicles;
	}
	public void addVehicle(Vehicle v) {	
		vehicles.add(v);
	}
	boolean removeVehicle(Vehicle v){
		return vehicles.remove(v);
	}
}
