package trafficsim;


//import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

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
		GraphUtils.addEdge(myMap,r.start_node,r.end_node);
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
	void update(double dt){
		
		myMap.updateNodes(dt);
		
		myMap.updateRoads(dt);

	}
	

	
	public static void main(String[] args) {
		
		System.out.println("Simulation started");
		Roadpathfinding();
		
		for (int i =0; i<myCont.getTotalTime(); i++){
		
			myCont.update(myCont.getStep());
		}

	}

	/** 
	 * this is how we add test cases.  Make similar static void methods
	 * that set myCont and myMap and get called from the above main method
	 */
	public static void create1Road(){
		myCont = new Controller(1000,1);
		myMap = new Map();
		Node v1 = new Source(1);
		v1.setX(0);v1.setY(0);
		myMap.addVertex(v1);
		Node v2 = new Node();
		v2.setX(100); v2.setY(0);
		myMap.addVertex(v2);
		Road r = new Road(0,"alcatraz",100,25,1,v1, v2);
		myMap.addRoad(r);
		Car v = new Car(r,0.0);
	}

	public static void readEditorMap(){
		
	}

	public static void Roadpathfinding(){
		myCont = new Controller(1000,1);
		myMap = new Map();
		Node v1 = new Source(1);
		v1.setX(0);v1.setY(0);
		myMap.addVertex(v1);
		Node v2 = new Node();
		v2.setX(100); v2.setY(0);
		myMap.addVertex(v2);
		Road r = new Road(0,"alcatraz",100,25,1,v1, v2);
		myMap.addRoad(r);
		myMap.initializeWeights();
		Car c = new Car(v1,v2);
	}
	
	public static void create2Road(){
		myCont = new Controller(100,1);
		myMap = new Map();
		
		Node v1 = new Source(1);
		v1.setX(0);v1.setY(0);
		myMap.addVertex(v1);
		
		Node v2 = new Node();
		v2.setX(100); v2.setY(0);
		myMap.addVertex(v2);
		
		Node v3 = new Node();
		v3.setX(400); v3.setY(0);
		myMap.addVertex(v3);
		
		
		Road r = new Road(0,"alcatraz",100,25,1,v1, v2);
		Road rr = new Road(1,"oslo",300,50,1,v2, v3);
		
		
		myMap.addRoad(r);
		myMap.addRoad(rr);
		
		
		Car v = new Car(r,0.12);
		Car follower = new Car(r,0.0);
		
		v.route.add(rr);
		follower.route.add(rr);
		follower.setCarInFront(v);
		follower.setSpeed(4);
		
	}
}
