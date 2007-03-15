package trafficsim;


public class Curve extends Road{
	
//	 parameters
	
//	radius of the curve
		public final double radius;
//	 which way the curve turns, 0 = left, 1 = right
		public final boolean way;
		
	
	public Curve(int id, String Name, double length, double speed_limit, int width, Node start, Node end,double r, boolean w) {
		super(id, Name, length, speed_limit, width, start, end);
		way = w;
		radius = r;
		
		// TODO Auto-generated constructor stub
	}

}
