package trafficsim;


import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Iterator;

import edu.uci.ics.jung.graph.ArchetypeEdge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValue;
import edu.uci.ics.jung.graph.decorators.UserDatumNumberEdgeValue;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;

/*
 * Roads are responsible for knowing the node which is starts and ends
 * Also roads have a list of vehicles (sorted by position) currently
 * on the road.  
 * 
 * NEW RULE:
 * Anytime a there is a change in speed limit (ie straight to curved road,
 * or just a random change) the roads nodes must be trafficControllers.
 * This ensures that the car is the correct speed and ends the need to 
 * look on roads ahead to find future speed limits
 */
public class Road extends DirectedSparseEdge {
	
	 private UUID id;
	//int id;
	 private String name;
	 private TreeSet<Vehicle> vehicles;	//list of current vehicles on the road
	 private double coeff_of_fric;	//the current coefficient of friction of the road
	 private double weather_coeff;	//higher weather coefficient means more rain and poorer coeff of friction
	 private double length;	//the legth of the road
	 private Node start_node; //the node at the start of the road
	 private Node end_node; //the node at the end of the road
	 private double speedLimit; //speed limit of the road
	 private double averageSpeed; // average speed of the current vehicles on the road
	 private double averageWeight; //average weight of the current vehicles on the road
	 private double roadAngle;  //the angle which the road is measured from positive x axis 
	
	 Road(Node n1, Node n2){
		super(n1,n2);
	}
public Road(String Name, double speed_limit, Node start, Node end ){
	super((Vertex)start,(Vertex)end);
	this.id = UUID.randomUUID();
	this.name =  Name;
	//speed limit converted to meters per second so miles/hour can be used as input
	this.speedLimit = speed_limit/2.237;
	this.start_node = start;
	this.end_node = end;
	this.coeff_of_fric = 0.7;
	this.weather_coeff = 0.0;
	vehicles = new TreeSet<Vehicle>();
	setRoadAngle();
	setLength();
}

private void setLength() {
	double startX = this.getStartNode().getX();
	double startY = this.getStartNode().getY();
	double endX = this.getEndNode().getX();
	double endY = this.getEndNode().getY();
	double dX = endX - startX;
	double dY = endY - startY;
	this.length = Math.sqrt(dX*dX + dY*dY);
}
public SortedSet<Vehicle> getVehicleList() {
	return this.vehicles;
}

public void addVehicle(Vehicle vehicle) {
	
	this.vehicles.add(vehicle);
}
public void removeVehicle(Vehicle vehicle) {
	vehicles.remove(vehicle);
}

public void updateVehicles(double dt){
		Iterator veh = vehicles.iterator();
		while (veh.hasNext()){
		Vehicle vehicle = (Vehicle)veh.next();
		vehicle.updateAcceleration();
		//vehicle.updatePosition(dt);
		//vehicle.update_stat(dt);
		//vehicle.updateSpeed(dt);
		//vehicle.printSpeed();
		//vehicle.printInfo();	
		}
	}


public void updatePosition(double dt){
	
	Iterator veh = vehicles.iterator();
	while (veh.hasNext()){
		Vehicle vehicle = (Vehicle)veh.next();
		
		//System.out.println("finding position");
		vehicle.updatePosition(dt);
		
		//System.out.println("updating stat");
		vehicle.update_stat(dt);
		
		//System.out.println("updating speed");
		vehicle.updateSpeed(dt);
		
		//System.out.println("printing info");
		vehicle.printInfo();
		//vehicle.printMaxBreaking();
		//vehicle.printMaxAcceleration();
		//vehicle.printSafeBreakingDist();
		//vehicle.printAccelerationToRoadsAhead();
	}
}

public int totalVehicles() {
	return vehicles.size();
}

public double getLimit(){
	return speedLimit;
}

public double getAvgSpeed2(){
	double sum = 0;
	Iterator veh = vehicles.iterator();
	while (veh.hasNext()){
		Vehicle vehicle = (Vehicle)veh.next();
		sum += vehicle.getSpeed();
	}
	return sum / totalVehicles();
}

public double getAvgSpeed(){
	Vehicle theCar;
	double[] speedArray;
	
	speedArray = new double[vehicles.size()];
	Iterator veh = vehicles.iterator();
	int i =0;
	while (veh.hasNext()){
		theCar= (Vehicle)veh.next();
		speedArray[i] = theCar.getSpeed();
		i=i+1;
	}
	
	double sumSpeed =0;
	for (int j=0; i < speedArray.length; i++){
		sumSpeed = sumSpeed + speedArray[i];
	}
	double avgSpeed = sumSpeed / vehicles.size();
	return avgSpeed;
}


/*
 * searches this road and finds every car from the percent
 * to the range
 */
SortedSet<Vehicle> searchRoad(Vehicle myV,double start, double range){
	SortedSet<Vehicle> carsOnStrip = new TreeSet<Vehicle>();
	
	Iterator veh = vehicles.iterator();
	//hmmm
	double endPerc = range/this.getLength();
	if (start +endPerc >1){
		//the car must search beyond current road on route
		double percentOver = 1- (start +endPerc);
		if (myV.hasNextRoadOnRoute()){
			Road nextRoad = myV.getNextRoadOnRoute();
			carsOnStrip = nextRoad.searchRoad(myV,0,percentOver*nextRoad.length);
		}
	}
	SortedSet<Vehicle> s = Collections.synchronizedSortedSet(new TreeSet(carsOnStrip));
	while (veh.hasNext()){
		Vehicle ve = (Vehicle)veh.next();
		if (ve.getPercent()>start && ve.getPercent()<range){
			s.add(ve);
		}
	}
	return s;
}
double distanceBetweenCars(Vehicle v1, Vehicle v2){
	if (v1.isOnSameRoad(v2)){
		return Math.abs(v1.getPercent()-v2.getPercent())*v1.getRoute().get(v1.getRoutePos()).getLength();
	}
	else{
		throw new IllegalArgumentException("not on same road");
	}
}
Vehicle findCarInFront(Vehicle myV, double start,double range){
	SortedSet<Vehicle> carsOnStrip = searchRoad(myV,start,range);
	Iterator veh = carsOnStrip.iterator();
	Vehicle inFront;
	if (veh.hasNext()){
		inFront = (Vehicle)veh.next();
	}else{
		inFront = new Nullvehicle();
	}
	return inFront;
}

private void printVehicleList() {
	Iterator veh = vehicles.iterator();
	while (veh.hasNext()){
		Vehicle vehicle = (Vehicle)veh.next();
		vehicle.printSpeed();
	}
}

boolean endNodeIsController(){
	Node n1 = getEndNode();
	if (n1.isTrafCont()){
		return true;
	}else{
		return false;
	}
}

public double getLength(){
	return this.length;
}

public double getCoeffOfFriction() {
	double coeffFric;
	coeffFric = this.coeff_of_fric - 0.25 * this.weather_coeff;		// Taken from 2-27 Mathematical Model.ppt
	return coeffFric;
}
boolean isOnRoad(Vehicle v){
	Iterator iter = vehicles.iterator();
	boolean ret = false;
	while (iter.hasNext()){
		if (((Vehicle)iter.next()).equals(v)){
			ret = true;
		}
	}
	return ret;
}

public String getID() {
	return this.id.toString();
}

boolean equals(Road r){
	return this.id==r.id;
}

public Node getStartNode() {
	return this.start_node;
}

public Node getEndNode() {
	return this.end_node;
}

public double getSpeedLimit() {
	return this.speedLimit;
}

/**
 * returns true if the vehicle is within the safe breaking distance
 * of the end node of this road.  Assumes vehicle is on this road
 * @param V
 * @return
 */
boolean isWithinSBD(Vehicle V){
	double SBD = V.getSafeBreakingDist();
	double distToEndNode = this.getLength() - V.getPercent()*this.getLength();
	return distToEndNode<=SBD;
}
public double getRoadAngle() {
	return roadAngle;
}
public void setRoadAngle() {
	double startX = this.getStartNode().getX();
	double startY = this.getStartNode().getY();
	double endX = this.getEndNode().getX();
	double endY = this.getEndNode().getY();
	double dX = endX - startX;
	double dY = endY - startY;
	roadAngle = Math.atan(dY/dX);
}
public TreeSet<Vehicle> getVehicles() {
	return vehicles;
}
}


