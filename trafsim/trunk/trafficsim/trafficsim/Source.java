package trafficsim;

import java.util.UUID;

//import java.awt.geom.Point2D;
import java.util.Random;

public class Source extends Node{

	//int sourceId;
	private Random U = new Random();
	
	private UUID sourceID;
	
	Source() {
		super();
		this.sourceID = UUID.randomUUID();
	}

	public String getID() {
		return this.sourceID.toString();
	}
	
	public String getSourceID() {
		return super.getID();
	}
	
	boolean isSource(){
		return true;
	}
	
//	old function
	//public double generate_next_car(double time){
		
		//double f = get_freqency(time);
		//double x = U.nextFloat();
		//double dt = (1/f)*Math.log(1-x);
		//return dt;
	//}
	
	//return true if a car should be created
	public boolean generate_new_car(){	
		boolean carCreation;
		//double f = get_freqency(time);
		double f = .3;
		double x = U.nextFloat();
		if(x < f){
			carCreation = true;
		//	System.out.println("A car is created at source: " + sourceId + " at time: " + time);
		}
		else{
			carCreation =false;
		}
		return carCreation;
	}
	
	public double getFrequency(){
		return 0.03;
	}
}
