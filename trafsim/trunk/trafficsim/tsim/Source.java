

import java.awt.geom.Point2D;
import java.util.Random;

public class Source extends Node{

	Source() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Random U;
	
	public int generate_next_car(double time){
		
		double f = get_freqency(time);
		double x = U.nextFloat();
		//int dt = (int)(1/f)*Math.log(1-x);
		return (int)x;
	}
	
	public double get_freqency(double time){
		return 0.03;
	}

}
