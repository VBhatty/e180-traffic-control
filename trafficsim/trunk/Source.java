package trafficsim;

import java.util.Random;

public class Source extends Node{

	private Random U;
	public Source() {
		
		U=new Random();
		
		// TODO Auto-generated constructor stub
	}
	
	public double generate_next_car(double time){
		
		double f = get_freqency(time);
		double x = U.nextFloat();
		double dt = (1/f)*Math.log(1-x);
		return dt;
	}
	
	public double get_freqency(double time){
		return 0;
	}

}
