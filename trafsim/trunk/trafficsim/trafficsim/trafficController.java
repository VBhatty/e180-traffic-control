package trafficsim;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
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
	public int carCnt=0;
	private ArrayList<Integer> totalCarsAtEachStep = new ArrayList<Integer>();
	private Queue<Vehicle> vehicles = new LinkedList<Vehicle>();

	boolean isTrafCont(){
		return true;
	}
	void setSpeedlimit(double limit){
		
		speedlimit = limit;
	}
	
	public double getSpeedLimit(){
		return speedlimit;
	}
	void addVehicle(Vehicle v) {	
		vehicles.add(v);
		carCnt+=1;
	}
	boolean removeVehicle(Vehicle v){
		return vehicles.remove(v);
	}
	/**
	 * updates state, speed limit and other functions
	 * of a trafficController
	 */
	void updateTrafCont(double dt){
		Iterator iter = vehicles.iterator();
		if(!vehicles.isEmpty()){
			Vehicle v = (Vehicle)vehicles.remove();
			v.setLoc_fraction(0);
			Road nextRoad = (Road) v.getRoute().get(v.getRoutePos());
			nextRoad.addVehicle(v);
			//v.getRoute().get(v.getRoutePos()).addVehicle(v);
			//v.addRoadID(nextRoad.getID());
		}
	}
	public Queue<Vehicle> getVehicles() {
		return vehicles;
	}
	public ArrayList<Integer> getTotalCarsAtEachStep() {
		return totalCarsAtEachStep;
	}
	public void addTotalCarsAtEachStep(Integer totalCarsAtEachStep) {
		this.totalCarsAtEachStep.add(totalCarsAtEachStep);
	}
}
