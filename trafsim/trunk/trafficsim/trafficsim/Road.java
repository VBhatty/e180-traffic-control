package trafficsim;


import java.util.ArrayList;

import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;


public class Road extends DirectedSparseEdge {
	
	int id;
	public String name;
	ArrayList <Vehicle>vehicles;	//list of current vehicles on the road
	public double coeff_of_fric;	//the current coefficient of friction of the road
	public double weather_coeff;	//higher weather coefficient means more rain and poorer coeff of friction
	public double length;	//the legth of the road
	public Node start_node; //the node at the start of the road
	public Node end_node; //the node at the end of the road
	public double speed_limit; //speed limit of the road
	public int width;	//number of lanes of the road
	public double average_speed; // average speed of the current vehicles on the road
	public double average_weight;	//average weight of the current vehicles on the road
	public Road(Node n1, Node n2){
		super(n1,n2);
	}
public Road(int id, String Name, double length, double speed_limit,int width, Node start, Node end ){
	super((Vertex)start,(Vertex)end);
	this.id = id;
	this.name =  Name;
	this.length = length;
	this.speed_limit = speed_limit;
	this.width = width;
	this.start_node = start;
	this.end_node = end;
	this.coeff_of_fric = 0.7;
	this.weather_coeff = 0.0;
	vehicles = new ArrayList<Vehicle>();
}

public ArrayList<Vehicle> get_vehicle_list() {
	return this.vehicles;
}

public void addVehicle(Vehicle vehicle) {
	this.vehicles.add(vehicle);
}
public void removeVehicle(Vehicle vehicle) {
	vehicles.remove(vehicle);
}

public void updateVehicles(double dt){
	for ( int i=0; i < vehicles.size();i++){
		Vehicle vehicle = (Vehicle)vehicles.get(i);
		
		vehicle.set_acceleration();
		vehicle.update_position(dt);
		vehicle.update_speed(dt);
		vehicle.printSpeed();
		//vehicle.printMaxBreaking();
		//vehicle.printMaxAcceleration();
		//vehicle.printSafeBreakingDist();
	}
}

public int totalVehicles() {
	return vehicles.size();
}

public double getLimit(){
	return speed_limit;
}

public double getAvgSpeed2(){
	double sum = 0;
	for (int i=0; i < vehicles.size(); i++) {
		Vehicle vehicle = (Vehicle)vehicles.get(i);
		sum += vehicle.get_speed();
	}
	return sum / totalVehicles();
}

public double getAvgSpeed(){
	Vehicle theCar;
	double[] speedArray;
	
	speedArray = new double[vehicles.size()];
	for ( int i=0; i < vehicles.size(); i++ ){
		theCar = (Vehicle)vehicles.get(i);
		speedArray[i] = theCar.cur_speed;
	}
	
	double sumSpeed =0;
	for (int i=0; i < speedArray.length; i++){
		sumSpeed = sumSpeed + speedArray[i];
	}
	double avgSpeed = sumSpeed / vehicles.size();
	return avgSpeed;
}

private void printVehicleList() {
	for (int i = 0; i<vehicles.size();i++){
		vehicles.get(i).printSpeed();
	}
}

public Node getEndNode(){
	return end_node;
}

public double getLength(){
	return this.length;
}

public double getCoeffOfFriction() {
	double coeffFric;
	coeffFric = this.coeff_of_fric - 0.25 * this.weather_coeff;		// Taken from 2-27 Mathematical Model.ppt
	return coeffFric;
}

}


