package trafficsim;

import java.util.UUID;

public class Sink extends Node{
	
	private UUID sinkID;
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
		carCnt+=1;
	}
	public void updateSink(double dt) {
		
	}
}
