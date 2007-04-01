package trafficsim;

import java.util.ArrayList;

public class Intersection extends trafficController {
	private ArrayList <Vehicle>waitVehicles;
	private ArrayList <Vehicle>incomingRoads;
	private ArrayList <Vehicle>outgoingRoads;
	private Map myIntersection;
	/*
	 * Constructs an intersection and creates a intersection map
	 * which connects incoming roads to outgoing roads
	 */
	Intersection(){
		
		myIntersection = new Map();
		
	}
}
