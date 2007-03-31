package trafficsim;


import java.util.ArrayList;
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
	public double average_weight;
	//average weight of the current vehicles on the road
	public Road(Node n1, Node n2){
		super(n1,n2);
	}
public Road(String Name, double length, double speed_limit,int width, Node start, Node end ){
	super((Vertex)start,(Vertex)end);
	this.id = UUID.randomUUID();
	this.name =  Name;
	this.length = length;
	//speed limit converted to meters per second so miles/hour can be used as input
	this.speed_limit = speed_limit/2.237;
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
	this.vehicles.add(0,vehicle);
}
public void removeVehicle(Vehicle vehicle) {
	vehicles.remove(vehicle);
}

public void updateVehicles1(double dt){
	
	for ( int i=0; i < vehicles.size() ;i++){
		Vehicle vehicle = (Vehicle)vehicles.get(i);
		
		//System.out.println("setting acceleration");
		vehicle.set_acceleration();
		
		//System.out.println("finding position");
		vehicle.updatePosition(dt);
		
		//System.out.println("updating stat");
		vehicle.update_stat(dt);
		
		//System.out.println("updating speed");
		vehicle.update_speed(dt);
		
		vehicle.update_data(dt);
		
		//System.out.println("printing info");
		vehicle.printInfo();
		//vehicle.printMaxBreaking();
		//vehicle.printMaxAcceleration();
		//vehicle.printSafeBreakingDist();
	}
}
public void updateVehicles(double dt){
	for ( int i=0; i < vehicles.size() ;i++){
		Vehicle vehicle = (Vehicle)vehicles.get(i);
		vehicle.updateAcceleration();
		vehicle.updatePosition(dt);
		vehicle.update_stat(dt);
		vehicle.update_speed(dt);
		vehicle.printInfo();	
	}
}

/*
 * updates every vehicle's acceleration on this road
 */
public void updateVehiclesAcceleration(double dt){
	
	for ( int i=0; i < vehicles.size() ;i++){
		Vehicle vehicle = (Vehicle)vehicles.get(i);
		vehicle.updateAcceleration();
		
	}
}

public void updateVehiclesPosition(double dt){
	
	for ( int i=0; i < vehicles.size() ;i++){
		Vehicle vehicle = (Vehicle)vehicles.get(i);
		
		
		//System.out.println("finding position");
		vehicle.updatePosition(dt);
		
		//System.out.println("updating stat");
		vehicle.update_stat(dt);
		
		//System.out.println("updating speed");
		vehicle.update_speed(dt);
		
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


/*
 * searches this road and finds every car from the percent
 * to the range
 */
ArrayList<Vehicle> searchRoad(Vehicle myV,double start, double range){
	ArrayList<Vehicle> carsOnStrip = new ArrayList<Vehicle>();
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
	while (veh.hasNext()){
		Vehicle ve = (Vehicle)veh.next();
		if (ve.getPercent()>start && ve.getPercent()<range){
			carsOnStrip.add(ve);
		}
	}
	return carsOnStrip;
}

Vehicle findCarInFront(Vehicle myV, double start,double range){
	ArrayList<Vehicle> carsOnStrip = searchRoad(myV,start,range);
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
	for (int i = 0; i<vehicles.size();i++){
		vehicles.get(i).printSpeed();
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
	return this.speed_limit;
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
}


