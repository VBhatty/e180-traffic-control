package trafficsim;
import java.util.ArrayList;


public class Road {
	
	int id;
	public String name;
	public ArrayList vehicles;	//list of current vehicles on the road
	public double coeff_of_friq;	//the current coefficient of friction of the road
	public double length;	//the legth of the road
	public Node start_node; //the node at the start of the road
	public Node end_node; //the node at the end of the road
	public double speed_limit; //speed limit of the road
	public int width;	//number of lanes of the road
	public double average_speed; // average speed of the current vehicles on the road
	public double average_weight;	//average weight of the current vehicles on the road
	
public Road(int id, String Name, double length, double speed_limit,int width, Node start, Node end ){
	this.id = id;
	this.Name =  Name;
	this.length = length;
	this.speed_limit = speed_limit;
	this.width = width;
	this.start_node = start;
	this.end_node = end;
}

public ArrayList get_vehicle_list() {
	return this.vehicles;
}

public void addVehicle(Vehicle vehicle) {
	vehicles.add(vehicle);
}
public void removeVehicle(Vehicle vehicle) {
	vehicles.remove(vehicle);
}

public void updateVehicles(){
	for ( int i=0; i < vehicles.size();i++){
		Vehicle vehicle = (Vehicle)vehicles.get(i);
		
		vehicle.findAcceleration();
		vehicle.updatePosition();
		vehicle.updateSpeed();
	}
}

public int totalVehicles() {
	return vehicles.size();
}

public double getLimit(){
	return speed_limit;
}

public double getAvgSpeed2(){
	sum = 0;
	for (int i=0; i < vehicles.size(); i++) {
		Vehicle vehicle = vehicles.get(i);
		sum += vehicle.get_speed();
	}
	return sum / totalVehicles();
}

public double getAvgSpeed(){
	Vehicle theCar;
	double[] speedArray;
	
	speedArray = new double[vehicles.size()];
	for ( int i=0; i < vehicles.size(); i++ ){
		theCar = vehicles(i);
		speedArray(i) = theCar.cur_speed;
	}
	
	double sumSpeed;
	for (int i=0; i < speedArray.size(); i++){
		sumSpeed = sumSpeed + speedArray(i);
	}
	avgSpeed = sumSpeed / vehicles.size();
	return avgSpeed
}

}


