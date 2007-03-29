package trafficsim;


public class Car extends Vehicle {

	public Car(Road r,double perc) {
		super(r,perc);
	}
	public Car(Node start,Node end) {
		super(start,end);
	}

	@Override
	boolean isNull() {
		// TODO Auto-generated method stub
		return false;
	}

}
