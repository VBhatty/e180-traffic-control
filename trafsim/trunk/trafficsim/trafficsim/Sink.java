package trafficsim;

import java.util.ArrayList;
import java.util.UUID;

public class Sink extends Node{
	
	private UUID sinkID;
	 private ArrayList<Integer> carsVTime =new ArrayList<Integer>();
	 private ArrayList<Vehicle> finishedVehicles = new ArrayList<Vehicle>();
	int carCnt;   //# of cars that have got to this sink as dest
	public Sink () {
		super();
		carCnt =0;
	}
	boolean isSink(){
		return true;
	}
	
	public String getID() {
		return super.getID();
	}
	
	public String getNodeID() {
		return super.getID();
	}
	void addVehicle (Vehicle v){
		finishedVehicles.add(v);
		carCnt+=1;
	}
	public void updateSink(double dt) {
		
	}
	public ArrayList<Integer> getCarsVTime() {
		return carsVTime;
	}
	public void addCarsVTime(Integer carsVTime) {
		this.carsVTime.add(carsVTime);
	}
}
