package trafficsim;

import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

public class fourWayStop extends trafficController{
	Queue<Vehicle> inRoad1List;
	Queue<Vehicle> inRoad2List;
	Queue<Vehicle> inRoad3List;
	Queue<Vehicle> inRoad4List;
	fourWayStop(){
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
	/**
	 * lets a max of 2 cars through at one time
	 */
	void updateTrafCont(double dt){
		generateInNodeVehList();
		//let one car go in each direction if it is added to the node
		if (!this.inRoad1List.isEmpty() || !inRoad2List.isEmpty()){
			//get the next vehicle to let through to next road on route
			Iterator road1Iter = inRoad1List.iterator();
			Iterator road2Iter = inRoad2List.iterator();
			Iterator road3Iter = inRoad3List.iterator();
			Iterator road4Iter = inRoad4List.iterator();
			int carCnt = 0;  //number of cars let through
			if (road1Iter.hasNext()){
				Vehicle v1 = (Vehicle)road1Iter.next();
				this.removeVehicle(v1);
				Road newRoad = v1.getRoute().get((v1.getRoutePos()));
				newRoad.addVehicle(v1);
				carCnt = carCnt+1;
			}
			if (road2Iter.hasNext()){
				Vehicle v2 = (Vehicle)road1Iter.next();
				this.removeVehicle(v2);
				Road newRoad = v2.getRoute().get((v2.getRoutePos()));
				newRoad.addVehicle(v2);
				carCnt =carCnt+1;
			}
			if (road3Iter.hasNext() && carCnt<2){
				Vehicle v3 = (Vehicle)road1Iter.next();
				this.removeVehicle(v3);
				Road newRoad = v3.getRoute().get((v3.getRoutePos()));
				newRoad.addVehicle(v3);
				carCnt =carCnt+1;
			}
			if (road4Iter.hasNext()&& carCnt<2){
				Vehicle v4 = (Vehicle)road1Iter.next();
				this.removeVehicle(v4);
				Road newRoad = v4.getRoute().get((v4.getRoutePos()));
				newRoad.addVehicle(v4);
				carCnt =carCnt+1;
			}
		}
	}
	void generateInNodeVehList(){
		//should be a set of 4
		Set InEdges = getInEdges();
		Iterator iter= InEdges.iterator();	
		Road r1 = (Road) iter.next();
		Road r2 = (Road) iter.next();
		Road r3 = (Road) iter.next();
		Road r4 = (Road) iter.next();
		
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
			if(v.getRoute().get(v.getRoutePos()-1).equals(r3)){
				inRoad3List.add(v);
			}
			if(v.getRoute().get(v.getRoutePos()-1).equals(r4)){
				inRoad4List.add(v);
			}
		}
	}
}