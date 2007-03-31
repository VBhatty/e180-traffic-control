package trafficsim;


public class Semi extends Vehicle {

	public Semi(Road r,double fract) {
		super(r,fract);
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean isNull() {
		// TODO Auto-generated method stub
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
