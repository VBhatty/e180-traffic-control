package trafficsim;



public class Nullvehicle extends Vehicle {
	public Nullvehicle() {
		super();
		// TODO Auto-generated constructor stub
	}
	boolean isNull(){
		return true;
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
