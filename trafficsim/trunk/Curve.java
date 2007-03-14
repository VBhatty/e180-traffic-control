package trafficsim;


public class Curve extends Road{

// parameters
	
//radius of the curve
	public final double radius;
// which way the curve turns, 0 = left, 1 = right
	public final boolean way;
	
	public Curve(double r, boolean w) {
		way = w;
		radius = r;
		// TODO Auto-generated constructor stub
	}

}
