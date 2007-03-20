package trafficsim;



public class Truck extends Vehicle{

	public Truck(Road r,double fract) {
		super(r,fract);
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean isNull() {
		return false;
	}

}
