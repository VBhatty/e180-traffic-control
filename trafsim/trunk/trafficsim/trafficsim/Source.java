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
	
	//return true if a car should be created and creates it
	//assumes road weights have been set 
	public boolean generate_new_car(){	
		boolean carCreation;
		//double f = get_freqency(time);
		double f = .3;
		double x = U.nextFloat();
		Map myMap = (Map)this.getGraph();
		Sink destination = myMap.getRandomSink();
		if(x < f){
			carCreation = true;
			new Car(this,destination);
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
