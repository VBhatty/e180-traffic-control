package trafficsim;

//import Vehicle;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.algorithms.shortestpath.ShortestPath;
import edu.uci.ics.jung.algorithms.shortestpath.ShortestPathUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collection;
//import java.util.Random;


import java.util.UUID;

import edu.uci.ics.jung.graph.ArchetypeGraph;

//import edu.uci.ics.jung.graph.Edge;

/**
 * Vehicle class is super class for all cars,  acceleration and speed are calculated
 * by the car at each time step.  Each car belongs to a road and has a user set mass
 * and length
 */
public abstract class Vehicle implements Comparable {

// Class parameters

	//	public static final double mass_mean = 1000; //may be used as distribution mean to generate mass
	public static final double g = 9.8;
	
	//these variables are specified in each extending instance
	private double maxVisibility;
	protected double mass;			//mass of this vehicle
	protected double length;	//length of this vehicle
	//movement variables
	private double speedX;
	private double speedY;
	private double accelX;	
	private double accelY;
	//position variables
	private Node startNode;
	private Node destination;
	private Road myRoad;		//will be obmitted because this Road is Route[0]
	private double loc_fraction; 	//fraction of current Route[0] that the front of the car has travelled
	private Vehicle car_in_front;	//Nearest vehicle in front of this vehicle at current route at current time
	// Some vehicle statistics
	private static double average_speed;	//Average speed since creation
	private static double distance_travelled;	//distance travelled since creation
	private static double time_since_creation;	// time elapsed since creation
		
	private List<Road> route;	//ArrayList containing the roads on the route from current road to
	private int routePos;  //position on route of the car, use this instead of deleting routes								//the road that ends in the destination node in chronological ordering
	

	private Collection<Double> percentageAlongRoads;
	private Collection<String> roadIds;
	private int spawnTime;
									
	private UUID id;
	
	// notUpdated is used so that one car cannot get it's position updated twice at the
	//same time step because it changes to a road that is not updated by the controller
	boolean notUpdated;
	
	/**
	 * Null car object is used if a car has no car in front
	 */
	abstract boolean isNull();
	/**
	 * this is the most used constructor.  Since vehicles are created
	 * at nodes.
	 */
	public Vehicle(Node start,Node endNode)
	{
		this.id = UUID.randomUUID();
		
		car_in_front=null;
		speedX=0;
		speedY=0;
		accelX=0;
		accelY=0;
		average_speed=0;
		distance_travelled=0;
		time_since_creation=0;
		destination=endNode;
		startNode = start;
		route = findRoute(start, endNode);
		routePos =0;
		this.setLoc_fraction(0);
		//myRoad = route.get(routePos);
		route.get(getRoutePos()).addVehicle(this);
		
		maxVisibility = 25;
		notUpdated=true;
		generate_mass_and_length();
	}
	/**
	 * empty constructor
	 */
	public Vehicle(){

	}
	
	/** 
	 * Creates a vehicle at the beginning of road r
	 */
	public Vehicle(Road r,double fract)
	{
		this.percentageAlongRoads = new ArrayList<Double>();
		this.roadIds = new ArrayList<String>();
		car_in_front=null;
		accelX=0;
		accelY=0;
		speedX=0;
		speedY=0;
		loc_fraction=fract;
		average_speed=0;
		distance_travelled=0;
		time_since_creation=0;
		destination=null;
		//myRoad = r;
		route = new ArrayList<Road>();
		route.add(r);
		r.addVehicle(this);
		//finding_route();
		//generate_mass_and_length();
		//set spawntime
		notUpdated=true;
		maxVisibility = 25;
		id = UUID.randomUUID();
	}
	
	public int getSpawnTime() {
		return this.spawnTime;
	}
	
	public String getID() {
		return this.id.toString();
	}
	public UUID getUUID() {
		return this.id;
	}
	public Node getSource() {
		return this.startNode;
	}
	
	public Node getDestination() {
		return this.destination;
	}
	
	public double getWeight() {
		return this.mass;
	}
	
	/**
	 * Manually sets the road of this car and recalculates route
	 */
	public void setMyRoad(Road r){
		
	}
	
	/**
	 * Manually sets the destination of this car
	 */
	void setDestination(Node n){
		destination=n;
	}
	
	/**
	 * generates the mass and length of this car
	 */
public void generate_mass_and_length()
{
	
}


/**
 * The maximum acceleration that this car can use
 */
public double max_acceleration()
{
	double aMax = 0;
	double Cf = route.get(0).getCoeffOfFriction();
	aMax = Cf * g;		// Taken from 2-27 Mathematical Model.ppt
	return aMax;
}

public Collection<Double> getPercentages() {
	return this.percentageAlongRoads;
}

public Collection<String> getRoads() {
	return this.roadIds;
}

/**
 * The maximum negative acceleration (breaking) that this car can use
 */
public double max_breaking() //Giving out maximum possible deceleration (breaking)
{
	double bMax = 0;
	double v = this.getSpeed();
	double Cf0 = 0.7; //route.get(0).getCoeffOfFriction();
	double Cfw = Cf0;		//Cf(w) and Cf(0) are different if weather-coeff. is set to other than zero
	double b = 0.01464176482072;		//Constants b and c taken from Physical Model.ppt and computed to fit the SI-system
	double c = 0.00201974953161;
	
	bMax = -Cfw / (Cf0 * (2*b + 3*c*v));
	return bMax;
}


public void printMaxBreaking(){		//Just for testing the function max_breaking()
	System.out.println(max_breaking());
}

public void printMaxAcceleration(){		//Just for testing the function max_acceleration()
	System.out.println(max_acceleration());
}

public void update_data(double dt) {
	this.percentageAlongRoads.add(loc_fraction);
	this.roadIds.add(route.get(getRoutePos()).getID());
	
}

/**
 * done after cur_accel is found on each step,and before cur_speed
 */
public void update_stat(double dt)
{
	// updates time_since_creation by adding the last timestep
	time_since_creation = time_since_creation + dt;
	
	//updates distance_travelled by adding the distance travelled the last timestep assuming constant acceleration
	double nextspeed =getSpeed() + getAccel()*dt;
	if (nextspeed > 0){
		distance_travelled =  distance_travelled + (getSpeed() + nextspeed)*0.5*dt;
	}else{
		distance_travelled =  distance_travelled + (getSpeed())*0.5*dt;
	}
	
	//updates average_speed by divide distance_travelled by time_since_creation
	average_speed= distance_travelled/time_since_creation;
}
public static void printStat(){
	System.out.println("The car has reached it's destination");
	System.out.println("Traveltime was: " + time_since_creation);
	System.out.println("The distance travelled was: " + distance_travelled);
	System.out.println("Average speed: " + average_speed);
}

public void updatePosition(double dt)
{
	if(notUpdated){
		
		double nextspeed =getSpeed() + getAccel()*dt;

		double dp;
		if (nextspeed > 0){
			dp = (getSpeed() + nextspeed)*0.5*dt;
		}else{
			dp = (getSpeed() + 0)*0.5*dt;
		}
	
		//update the fraction of road travelled
		double fraction = getPercent() + dp/(route.get(routePos).getLength());
		
		// if the distance travelled is longer than what is left of current road,
		// the vehicle start on the next road on the route
		while ( fraction > 1)
		{
			//calculating how much longer than what is left of the road that the vehicle have
			//travelled in current timestep
			fraction = fraction - 1;
			//dp = fraction*route.get(routePos+1).getLength();
			//removes this vehicle
			route.get(routePos).removeVehicle((Vehicle)this);
			
		
			//if route is empty, then the vehicle has reached the destination
			if (routePos ==route.size()-1 && this.getRoute().get(routePos).getEndNode().isSink()){
				fraction = 0;//sets the fraction to zero to get out of the while loop, but
						// doesn't add the vehicle to a new road as the routelist is 
			            // is empty
				Sink s = (Sink)this.getRoute().get(routePos).getEndNode();
				s.addVehicle(this);
				this.printStat();
				//routePos = routePos +1;
			}
			else if (this.getRoute().get(routePos).getEndNode().isTrafCont()){
				trafficController mine = (trafficController)this.getRoute().get(routePos).getEndNode();
				double angle =this.getRoute().get(routePos).getRoadAngle();
				this.setSpeedX(mine.getSpeedLimit()*Math.cos(angle));
				this.setSpeedY(mine.getSpeedLimit()*Math.sin(angle));
				mine.addVehicle(this);
				fraction = 0;
				routePos = routePos +1;
			}
			//the vehicle is moving to a new road
			else{
				//add this vehicle to vehicles (the list of vehicles at the road) at 
				//next road on the route
				routePos = routePos +1;
				//fraction = dp/(route.get(routePos).getLength());
				fraction = 0;
				(route.get(routePos)).getVehicles().add(this);
				// calculating fraction completed at next road on route
				//myRoad =route.get(routePos);
			}
		
		}
	
		// setting loc_fraction equal the fraction completed at current road
		loc_fraction = fraction;
		notUpdated=false;
	}
}


/**
 * updates the position of this vehicle
 */
public void update_position1(double dt )
{
	if(notUpdated){
		
		double nextspeed =getSpeed() + getAccel()*dt;
	
		//distance travelled at current step
		double dp;
		if (nextspeed > 0){
			dp = (getSpeed() + nextspeed)*0.5*dt;
		}else{
			dp = (getSpeed() + 0)*0.5*dt;
		}
	
		// adding the distance travelled to the fraction of the current road
		double fraction = loc_fraction + dp/(route.get(0).getLength());
		int count;
		

		// if the distance travelled is longer than what is left of current road,
		// the vehicle start on the next road on the route
		while ( fraction > 1)
		{
			//calculating how much longer than what is left of the road that the vehicle have
			//travelled in current timestep
			fraction = fraction - 1;
			dp = fraction*route.get(0).getLength();
			//finding number of vehicles on current road
			count = route.get(0).totalVehicles()-1;
			//removes the last vehicle, which is this one
			(route.get(0).getVehicles()).remove(count);
		
		
		
			//remove completed road from route
			route.remove(0);
		
			//if route is empty, then the vehicle has reached the destination
			if (route.isEmpty() == true){
				fraction = 0;//sets the fraction to zero to get out of the while loop, but
						// doesn't add the vehicle to a new road as the routelist is 
			            // is empty
				this.printStat();
			}
			//the vehicle is moving to a new road
			else{
				//add this vehicle to vehicles (the list of vehicles at the road) at 
				//next road on the route
				(route.get(0)).getVehicles().add(this);
				// calculating fraction completed at next road on route
				fraction = dp/(route.get(0).getLength());
				//myRoad =route.get(0);
			}
		
		}
	
		// setting loc_fraction equal the fraction completed at current road
		loc_fraction = fraction;
		notUpdated=false;
	}
}

public void updateSpeed(double dt) //call in the end of timestep as the other updatefunctions use speed at  
									// start of time step as getSpeed()
{
	double nextspeedX =speedX + accelX*dt;
	double nextspeedY =speedY + accelY*dt;
	if (nextspeedX > 0){
		setSpeedX(nextspeedX);
	}else{
		setSpeedX(0);
	}
	if (nextspeedY > 0){
		setSpeedY(nextspeedY);
	}else{
		setSpeedY(0);
	}
}


/**
 * set the x speed to some fixed speed
 */
public void setSpeedY(double x){
	speedY = x;
}

public double getLength() {
	return this.length;
}

//Going through all the ahead roads on the vehicles route and calculating what breaking
//is needed to obtain the ahead roads speedlimit. The hardest deceleration is chosen.
//Note: This method ONLY consider roads.
public double accelerationToRoadsAhead(){
	double dist = 0;
	double a = getAccel();	//current acceleration
	double accNew = 10000;		//new acceleration sat high so it won't be chosen unless it's lower than current acc.
	double prefBreakDist = this.getSafeBreakingDist() * 1.2;	//Preferred (comfortable) breaking distance
	double mySpeed = this.getSpeed();	//Current speed
	double speedLimit;			//Speedlimit of roads ahead
	
	if (route.size() > 0){		//Preventing systemcrash when car is out of system/no roads on route
	dist = route.get(0).getLength() * (1 - this.loc_fraction);	//Distance from car to next road
	}
	
	int i = 1;					//counter, stepping 1 road ahead each time
	
	while(dist<prefBreakDist){
		if (i >= route.size()){			//If sink ahead, iow. NO new road, skip out of while-loop
			break;
		}
		speedLimit = route.get(i).getSpeedLimit();		//Speedlimit on roads ahead of current road
		if (speedLimit < mySpeed){					//Only necessary to break if new speedlimit is less
			accNew = (Math.pow(speedLimit,2) - Math.pow(mySpeed,2)) / (2*dist);
			if (accNew < a){						//Choosing hardest breaking decelration
				a = accNew;
			}
		}
		dist = dist + route.get(i).getLength();			//Updating distance
		i = i + 1;		
	}
	if (a > 0){
		a = 0;
	}
	/*if (a < max_breaking()){
		a = max_breaking();
	}*/
	return a;
	
}

//Testing accelerationToRoadsAhead() by writing out data
public void printAccelerationToRoadsAhead(){
	System.out.println("Acceleration to road ahead is: " + accelerationToRoadsAhead());
}

/**
 * New idea:
 * car accelerates so to be at the speed limit if its percent is < .50, if its greater
 * then the car acclerates to be at the speed limit at the next node, if it is 
 * a trafCont.  A trafCont exists anytime there is a change in speed limit, so if 
 * there is no trafCont then the speed limit on the next road is the same. So the
 * acceleration is calculated by the acceleration needed to be at the speed limit 
 * by the 
 * 
 * @return
 */
double accelerationToNearestNodeOrRoad1(){
	double accel = 0;
	Node myNode = this.getNextNodeOnRoute();
	double roadSpeedLimit = this.getRoute().get(routePos).getSpeedLimit();
	double mySpeed = this.getSpeed();
	double roadThresh = Math.abs(1- mySpeed/roadSpeedLimit);
	if (this.getPercent() < .5 && roadThresh>.9)
	{
		double accelDistance = this.getRoute().get(routePos).getLength()*.5;
		accel = (Math.pow(roadSpeedLimit, 2) - Math.pow(mySpeed, 2))/(2*accelDistance);
	}
	else if (this.getPercent() >= .5 && myNode.isTrafCont())
	{
		double nodeSpeedLimit = ((trafficController)myNode).getSpeedLimit();
		double nodeThresh = Math.abs(1- mySpeed/nodeSpeedLimit);
		double distanceToNode = (1- this.getPercent())*this.route.get(routePos).getLength();
		if (nodeThresh>.9){
		accel = (Math.pow(nodeSpeedLimit, 2) - Math.pow(mySpeed, 2))/(2*distanceToNode);
		}
	}else
	{
		double accelDistance = this.getRoute().get(routePos).getLength()*.5;
		accel = (Math.pow(roadSpeedLimit, 2) - Math.pow(mySpeed, 2))/(2*accelDistance);
	}
	
	return accel;
}
//Searching through all roads on route within safe breaking distance. If the speed limit changes within
//safe breaking distance calculate acceleration needed to get new speedlimit at that point.
public double accelerationToNearestNodeOrRoad(){
	double dist = 0;
	double endNodeSpeedLimit=0;
	double safeBreakDist = this.getSafeBreakingDist();
	double dist2newSpeedLimit = 0;
	double currSpeed = this.getSpeed();
	double roadSpeedLimit;
	
	int i = 0;
	double newSpeedLimit=0;
	while(dist<safeBreakDist){		//Going through roads at vehicle's route
		roadSpeedLimit = route.get(i).getLimit();
		if (route.get(i).getEndNode().isTrafCont()){
			endNodeSpeedLimit = ((trafficController)route.get(i).getEndNode()).getSpeedLimit();
		}
		if (i != 0) {				//Calculating distance to end of road / endnode start of new road
			dist2newSpeedLimit = dist2newSpeedLimit + route.get(i).getLength();		//If checking a road ahead dist2newSpeedLimit equals the sum of the road lengths + fraction of current road not driven
		} else {
			dist2newSpeedLimit = (route.get(i).getLength() * (1-this.loc_fraction)); 	//If checking current road, distance to change in speedlimit = the fraction of road not yet driven
		}
		
		
		if (roadSpeedLimit < currSpeed) {		//Comparing current vehicle speed to speedlimit of at each road ahead
			newSpeedLimit = roadSpeedLimit;			//If change in speedlimit at a road, get new speedlimit and end while-loop to calculate acceleration
			dist2newSpeedLimit = dist2newSpeedLimit - route.get(i).getLength();		//Change in speedlimit at a road starts at the end of the road
			break;
		} else if (route.get(i).getEndNode().isTrafCont()&& endNodeSpeedLimit < currSpeed) {
			newSpeedLimit = endNodeSpeedLimit;		//If change in speedlimit at the endnode of the road, get new speedlimit and end while-loop to calculate acceleration
			break;
		}
		i = i+1;
	}
	double nec = (Math.pow(newSpeedLimit,2) - Math.pow(currSpeed,2)) / (2*dist2newSpeedLimit);		//Necessary acceleration to reach new speedlimit
	return nec;
}



 double getSpeed()
{
	return Math.sqrt(speedX*speedX + speedY*speedY);
}
double getAccel(){
	return Math.sqrt(accelX*accelX + accelY*accelY);
}


public void printSpeed() 
{
	System.out.println(getSpeed());
}


public List<Road> findRoute(Node n1, Node n2)
{
	ArchetypeGraph myMap = this.startNode.getGraph();
	DijkstraShortestPath dij = new DijkstraShortestPath( myMap,((Map)myMap).getWeight());
	return ShortestPathUtils.getPath(((ShortestPath)dij),n1,n2);
}

//Finding the safe breaking distance of a vehicle
public double getSafeBreakingDist(){
	double v = 2.236936292*getSpeed();		//Current speed of vehicle converted from m/s to mph
	double safeDist;
	double Cf = route.get(0).getCoeffOfFriction();
	
	safeDist =  (0.7/Cf)* ((88/50)*v + (24/Math.pow(50,2))*Math.pow(v,2) + (74/Math.pow(50,3))*Math.pow(v,3)); //Formula from physical model
	safeDist = 0.3048*safeDist; //Safe breaking distance converted from feet to meter
	return safeDist;
}

public void printSafeBreakingDist(){		//Just for testing the function
	System.out.println(getSafeBreakingDist());
}
public double getDistance_travelled(){
	return distance_travelled;
}
public void printInfo(){
	System.out.println("Distance travelled is " + getDistance_travelled());
}

//another version of AccelerationDueToCarInFront
public double AccelerationDueToCarInFront(){
	//initializing target acceleration
	double a;
	//mbd = breakingdistance by applying maximum breaking, or "faen te h�stkukbr�msing"
	double mbd= getSafeBreakingDist();
	//pbd = breakingdistance by applying prefered breaking
	double pbd= 1.5*mbd;//might be modified later on
	
	
	//finding the distance to car in front
	double distance;

	// If no car in front, there is no reason to accelerate to adapt to the car in front
		if (car_in_front == null)
		{
			a=1000;
			//distance=pbd;
		}
	//there is a car in front:
		else if(car_in_front.route.isEmpty() == true){
			
			a=1000; 
		}
		else
		{
			//safe following distance
			double sfd = pbd + car_in_front.getLength()-mbd;
			// r - the current road of the car in front
			Road r = (car_in_front.route).get(0);
		
			// f - the current fraction of the road the car in front has completed
		 	double f = car_in_front.getPercent();
		 	
		 	
		 	//search through route to find distance to the point f (fraction of the road) on r (Road),
		 	//update dist while searching
		 	if (route.get(0) == r){
		 		distance = (f-loc_fraction)*r.getLength();
		 	}
		 	else{
			
		
		 		distance = (1-loc_fraction)*(route.get(0)).getLength();
		 		int i = 1;
		 		while(route.get(i) != r){
		 			distance = distance + (route.get(i)).getLength();
		 			i = i+1;
		 		}
		 		distance = distance + f*r.getLength();
		 		}
		
		
		 	// using constant acceleration equation to calculate the acceleration needed
	        //nec = 0.5*(v-getSpeed())*(v+getSpeed())/dist;
		 		//System.out.println(distance);
			if (distance < sfd){
				a = 0.8*max_breaking();
				System.out.println("BREAKING");
			}
			else{
				a=1000;
				
			}
	
		}
	return a;
}

double getPercent(){
	return loc_fraction;
}




public void setSpeedX(double x){
	speedX=x;
}
public void setCarInFront(Vehicle carInFront){
	car_in_front = carInFront;
}

/**
 * checks all possible sources of acceleration of this vehicle
 * and updates the acceleration equal to the minimum of the 
 * sources.
 */
public void updateAcceleration() {
	//the acceleration due to the car in front
	
	if (route.size()>routePos) {
		double carAccel = accelerationDueToCar();
		double nodeRoadAccel = accelerationToNearestNodeOrRoad1();
		double min = Math.min(carAccel, nodeRoadAccel);
		accelX = min * Math.cos(route.get(routePos).getRoadAngle());
		accelY = min * Math.sin(route.get(routePos).getRoadAngle());
		notUpdated = true;
	}	
}

/**
 * find the acceleration due to the speed limit of the current
 * road.
 * 
 * This is done by finding the acceleration needed to be at the
 * speedlimit in one safe breaking distance.
 * Does this work okay?
 */
private double accelerationDueToLimit() {
	Road myR = this.route.get(0);
	double speedLimit = myR.getLimit();
	double mySpeed= this.getSpeed();
	double accel = Double.POSITIVE_INFINITY;
	if (getAccel() < this.max_acceleration() && mySpeed<speedLimit){
		//for now accelerate to half the max acceleration
		accel = (this.max_acceleration()-getAccel())/2;
	}else{
		
		accel = (getAccel()-this.max_acceleration())/2;
	}
	return accel;
}

/**
 * finds the acceleration due to traffic controller in breaking range
 * returns 0 if no controller in sight
 */
private double accelerationDueToTrafficCont() {
	//find nearest traffic controller
	Node myNode = this.getNextNodeOnRoute();
	double accel;
	double speedLimit;
	double distanceToNode;
	distanceToNode = (1- this.getPercent())*this.route.get(0).getLength();
	speedLimit = ((trafficController)myNode).getSpeedLimit();
	double mySpeed = this.getSpeed();
	if (myNode.isTrafCont() && distanceToNode <= maxVisibility){
		 accel = (Math.pow(speedLimit, 2) - Math.pow(mySpeed, 2))/(2*distanceToNode);
	} else {
		return Double.POSITIVE_INFINITY;
	}
	return accel;
}

/**
 * New Idea:
 * If there is a car within visibilty of this car then within a threshold,
 * find the acceleration needed to be at the car in fronts speed when this car
 * is in the same position as the car in front
 */
private double accelerationDueToCar(){
	double accel = Double.POSITIVE_INFINITY;
	Vehicle v;
	double endFrac = this.getVisRange()/route.get(getRoutePos()).getLength();
	if (this.loc_fraction + endFrac<1){
		 v = route.get(getRoutePos()).findCarInFront(this,this.loc_fraction, this.loc_fraction + endFrac);
	}else{
		 v = route.get(getRoutePos()).findCarInFront(this,this.loc_fraction, 1);
	}
	if (v.isNull()){
		return accel;
	}
	else
	{
		double v1 = this.getSpeed();
		double v2 = v.getSpeed();
		double carThresh = Math.abs(1- v1/v2);
		double dist = route.get(getRoutePos()).distanceBetweenCars(this, v);
		if (carThresh>.9){
			accel = (Math.pow(v2, 2) - Math.pow(v1, 2))/(2*dist);
		}
	}
	return accel;
}

/**
 * finds distance between this car and car V1.  Assumes car is incident
 * with this cars route.  Iterates through route until vehicle parameter is found
 * and calculates distance
 */
double distanceBetCars(Vehicle V1){
	double distance;
	if(this.route.get(0).isOnRoad(V1)){
		distance = Math.abs((this.getPercent() - V1.getPercent())*this.route.get(0).getLength());
	}else{
		distance = (1-this.getPercent())*this.route.get(0).getLength();
		for (int i = 0; i<this.route.size(); i++){
			if(this.route.get(i).isOnRoad(V1)){
				distance = distance + V1.getPercent()*this.route.get(i).getLength();
			}else{
				distance = distance + this.route.get(i).getLength();
			}
		}
	}
	return distance;
}
/**
 * compare function to allow vehicle sorting by position
 */
int compareTo(Vehicle V2){
	int ret=0;
	if (this.equals(V2)){
		ret= 0;
	} else if (this.getPercent()< V2.getPercent()){
		ret= -1;
	}else if (this.getPercent()> V2.getPercent()){
		ret= 1;
	}
	return ret;
	}

/**
 * equals function to allow vehicle sorting by position
 */
public boolean equals(Vehicle V1){
	return this.getUUID()==V1.getUUID();
}
/**
 * gets the next roads on the route
 */
Road getNextRoadOnRoute(){
	if (this.route.size() >=routePos-1 ){
		return this.route.get(routePos+1);
	}else{
		return null;
	}
}
/**
 * returns true if there is a next road
 */
boolean hasNextRoadOnRoute(){
	if (this.route.size()==1){
		return false;
	}else{
		return true;
	}
}
/**
 * gets the next node on the route
 */
Node getNextNodeOnRoute(){
	return this.route.get(0).getEndNode();
}

boolean isAtDestination(double percent){
	if (route.get(getRoutePos()).equals(destination) && destination.isSink() && percent>=1){
		return true;
	}
	else{
		return false;
	}
}
double getVisRange(){
	return maxVisibility;
}
double getXpos(){
	//i dunno if we need this
	return 0;
	
}
double getYpos(){
	//i dunno if we need this
	return 0;
	
}
public List<Road> getRoute() {
	return route;
}
boolean isOnSameRoad(Vehicle V){
	return this.route.get(getRoutePos()).equals(V.getRoute().get(getRoutePos()));
}
public int getRoutePos() {
	return routePos;
}
public Road getMyRoad() {
	return route.get(getRoutePos());
}
public void setLoc_fraction(double loc_fraction) {
	this.loc_fraction = loc_fraction;
}
public void setRoutePos(int routePos) {
	this.routePos = routePos;
}
}