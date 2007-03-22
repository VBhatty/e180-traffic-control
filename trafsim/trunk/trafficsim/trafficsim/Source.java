package trafficsim;



//import java.awt.geom.Point2D;
import java.util.Random;

public class Source extends Node{

	int sourceId;
	private Random U = new Random();
	Source(int id) {
		super();
		this.sourceId=id;
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
	public boolean generate_new_car(int time){
		
		boolean carCreation;
		double f = get_freqency(time);
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
	
	public double get_freqency(int time){
		return 0.03;
	}

}
