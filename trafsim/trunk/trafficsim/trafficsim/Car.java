package trafficsim;


public class Car extends Vehicle {

	public Car(Road r,double perc) {
		super(r,perc);
		mass = 1500;
		length = 0;
		
	}
	public Car(Node start,Node end) {
		super(start,end);
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
