 package trafficsim;


//import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import sun.security.action.GetBooleanAction;

//import edu.uci.ics.jung.graph.DirectedEdge;
//mport edu.uci.ics.jung.graph.Graph;
//import edu.uci.ics.jung.graph.Vertex;
//import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
//import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
//import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;
import edu.uci.ics.jung.utils.GraphUtils;

public class Controller {
// fields
	static Controller myCont;
	int totaltime;
	final int dt;
	int current_time;
	boolean[][] mapGrid;
	static Map myMap; 
	
	/**
	 * constructor for simulation controller
	 */
	public Controller(int totTime, int step ){
		myMap = new Map();
		totaltime = totTime;
		dt = step;
		current_time = 0;

	}
	/**
	 * adds a node to the static map myMap
	 */
	void addNode(Node n){
		myMap.addVertex(n);
	}

	/**
	 * adds a raod to the static map myMap
	 */
	public void addRoad(Road r) {
		GraphUtils.addEdge(myMap,r.getStartNode(),r.getEndNode());
	}
	/**
	 * gets the current time in the simulation
	 */
	double getCurrentTime(){
		return current_time;
	}
	/**
	 * gets the step size for the simulation
	 */
	double getStep(){
		return dt;
	}
	/**
	 * gets the elapsed time of the simulation
	 */
	double getTotalTime(){
		return totaltime;
	}
	
	/** 
	 * please comment, what is i???
	 * i dunno!  its gone!
	 */
	void update(double dt, int i){
		
		myMap.updateRoads(dt, i);
		myMap.updateNodes(dt, i);

	}
	

	
	public static void main(String[] args) {
		
		System.out.println("Simulation started");
		Roadpathfinding();
		
		for (int i =0; i<myCont.getTotalTime(); i++){
		
			myCont.update(myCont.getStep(), i);
		}
	}

	/** 
	 * this is how we add test cases.  Make similar static void methods
	 * that set myCont and myMap and get called from the above main method
	 */
	public static void create1Road(){
		myCont = new Controller(1000,1);
		myMap = new Map();
		//Node v1 = new Source(1);
		Node v1 = new Source();
		v1.setX(0);v1.setY(0);
		myMap.addVertex(v1);
		Node v2 = new Node();
		v2.setX(100); v2.setY(0);
		myMap.addVertex(v2);
		Road r = new Road("alcatraz",25,v1, v2);
		myMap.addRoad(r);
		Car v = new Car(r,0.0);
	}

	public static void readEditorMap(){
		
	}

	public static void Roadpathfinding(){
		myCont = new Controller(1000,1);
		myMap = new Map();
		Node v1 = new Node();
		v1.setX(0);v1.setY(0);
		myMap.addVertex(v1);
		Node v2 = new trafficController();
		((trafficController)v2).setSpeedlimit(0);
		v2.setX(100); v2.setY(0);
		myMap.addVertex(v2);
		Road r = new Road("alcatraz",25,v1, v2);
		myMap.addRoad(r);
		myMap.initializeWeights();
		new Car(v1,v2);
	}
	
	public static void create2Road(){
		myCont = new Controller(100,1);
		myMap = new Map();
		
		//Node v1 = new Source(1);
		Node v1 = new Source();
		v1.setX(0);v1.setY(0);
		myMap.addVertex(v1);
		
		Node v2 = new Node();
		v2.setX(100); v2.setY(0);
		myMap.addVertex(v2);
		
		Node v3 = new Node();
		v3.setX(400); v3.setY(0);
		myMap.addVertex(v3);
		
		
		Road r = new Road("alcatraz",25,v1, v2);
		Road rr = new Road("oslo",50,v2, v3);
		
		
		myMap.addRoad(r);
		myMap.addRoad(rr);
		
		
		Car v = new Car(r,0.12);
		Car follower = new Car(r,0.0);
		
		v.getRoute().add(rr);
		follower.getRoute().add(rr);
		follower.setCarInFront(v);
		//follower.setSpeed(4);
		
	}
	public static void create2RoadSS(){
		myCont = new Controller(100,1);
		myMap = new Map();
		
		//Node v1 = new Source(1);
		Node v1 = new Source();
		v1.setX(0);v1.setY(0);
		myMap.addVertex(v1);
		
		Node v2 = new Node();
		v2.setX(100); v2.setY(0);
		myMap.addVertex(v2);
		
		Node v3 = new Node();
		v3.setX(400); v3.setY(0);
		myMap.addVertex(v3);
		
		
		Road r = new Road("alcatraz",25,v1, v2);
		Road rr = new Road("oslo",50,v2, v3);
		
		
		myMap.addRoad(r);
		myMap.addRoad(rr);
		
		
		Car v = new Car(r,0.12);
		Car follower = new Car(r,0.0);
		
		v.getRoute().add(rr);
		follower.getRoute().add(rr);
		follower.setCarInFront(v);
		//follower.setSpeed(4);
		
	}
}
