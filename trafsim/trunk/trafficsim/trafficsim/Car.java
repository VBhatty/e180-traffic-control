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

}
