package trafficsim;
/*
 * This class is meant to be the super class for traffic control objects
 * such as lights, stopsigns, yield signs etc. The idea is you create a specific type of node
 * at such junctions and the car does searches for Cars and trafficControllers
 * and adjusts its acceleration accordingly
 */
public class trafficController extends Node{
	private double speedlimit;
	
	boolean isTrafCont(){
		return true;
	}
	void setSpeedlimit(double limit){
		
		speedlimit = limit;
	}
	
	public double getSpeedLimit(){
		return speedlimit;
	}
	
	/*
	 * updates state, speed limit and other functions
	 * of a trafficController
	 */
	void updateTrafCont(double dt){
		
	}
	
}
