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
	Source (UUID id){
		super();
		this.sourceID=id;
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
	public boolean generate_new_car(double time){	
		boolean carCreation;
		double f = getFreqency(time);
		//double f = .3;
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
	
	
	//sourcefunction that returns high frequency in the morning
	//and afternoon, and low during nighttime
	public double getFreqency(double time){
		
		int day = 24*60*60;
	
		while (time >= 0){
			time =time-day;
		}
		
		time = time + day;
		time = time/3600;
		
		
		
		double f=0;
		double pi=3.1415;
		if(time < 6){
			f = 4+2*Math.cos(pi*time/3);
		}
		else if (time <9.5){
			f = 26+20*Math.cos(2*pi*(time-8)/4);
		}
		else if (time <17){
			f=12;
		}
		else if (time < 23){
			f = 17+5*Math.cos(2*pi*(time-20)/6);
		}
		else if (time < 24.1){
			f= 12-6*(time-23);
		}
		f = f/3600;
		
		return f;
	}

	
	}
