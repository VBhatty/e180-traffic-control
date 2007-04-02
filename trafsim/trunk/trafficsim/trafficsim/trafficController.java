package trafficsim;

import java.util.Queue;
import java.util.Stack;
import java.util.TreeSet;

/**
 * This class is meant to be the super class for traffic control objects
 * such as lights, stopsigns, yield signs etc. The idea is you create a specific type of node
 * at such junctions and the car does searches for Cars and trafficControllers
 * and adjusts its acceleration accordingly
 **/
public class trafficController extends SuperNode{
	private double speedlimit;
	private Queue<Vehicle> vehicles;
	boolean isTrafCont(){
		return true;
	}
	void setSpeedlimit(double limit){
		
		speedlimit = limit;
	}
	
	public double getSpeedLimit(){
		return speedlimit;
	}
	public void addVehicle(Vehicle v) {	
		vehicles.add(v);
	}
	boolean removeVehicle(Vehicle v){
		return vehicles.remove(v);
	}
	/**
	 * updates state, speed limit and other functions
	 * of a trafficController
	 */
	void updateTrafCont(double dt){
		
	}
	public Queue<Vehicle> getVehicles() {
		return vehicles;
	}
}
