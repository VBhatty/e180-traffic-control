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
	
	/* 
	 * constructor for simulation controller
	 */
	public Controller(int totTime, int step ){
		myMap = new Map();
		totaltime = totTime;
		dt = step;
		current_time = 0;

	}
	
	double getCurrentTime(){
		return current_time;
	}
	double getStep(){
		return dt;
	}
	double getTotalTime(){
		return totaltime;
	}
	double getEllapsed(){
		return 10;
	}
	void updateRoad(double dt){
		Set<Road> myRoads = myMap.getEdges();
		Iterator ro = myRoads.iterator();
		while(ro.hasNext()){
			Road rr = (Road)ro.next();
			rr.updateVehicles(dt);
		}
	}
	
	public int totalVehicles() {
		int total = 0;
		Set<Road> myRoads = myMap.getEdges(); 
		Iterator ro = myRoads.iterator();
		while (ro.hasNext()){
			Road rr =(Road) ro.next();
			total += rr.totalVehicles();
		}
		return total;
	}
	
	void addNode(Node n){
		myMap.addVertex(n);
	}

	public void addRoad(Road r) {
		GraphUtils.addEdge(myMap,r.start_node,r.end_node);
	}
	
	
	public static void main(String[] args) {
		create1Road();
		
		for (int i =0; i<myCont.getTotalTime(); i++){
			myCont.updateRoad(myCont.getStep());
		}

	}

	/* 
	 * this is how we add test cases.  Make similar static void methods
	 * that set myCont and myMap and get called from the above main method
	 */
	public static void create1Road(){
		myCont = new Controller(1000,1);
		myMap = new Map();
		Node v1 = new Node();
		v1.setX(0);v1.setY(0);
		myMap.addVertex(v1);
		Node v2 = new Node();
		v2.setX(100); v2.setY(0);
		myMap.addVertex(v2);
		Road r = new Road(0,"alcatraz",100,25,1,v1, v2);
		myMap.addRoad(r);
		Car v = new Car(r,0.0);
	}
}
