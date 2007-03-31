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
	public int compareTo(Object arg) {
		int ret=0;
		if (this.equals((Vehicle)arg)){
			ret= 0;
		} else if (this.getPercent()< ((Vehicle)arg).getPercent()){
			ret= -1;
		}else if (this.getPercent()> ((Vehicle)arg).getPercent()){
			ret= 1;
		}
		return ret;
	}
}
