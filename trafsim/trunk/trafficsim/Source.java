package trafficsim;

import java.awt.geom.Point2D;
import java.util.Random;

public class Source extends Node{

	Source(Point2D location, String n) {
		super(location, n);
	}

	private Random U;
	
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
