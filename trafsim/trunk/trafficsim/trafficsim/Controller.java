 package trafficsim;


//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.e180.Parser.FileParser;
import com.e180.vo.NodeVO;
import com.e180.vo.RoadVO;
import com.e180.vo.SceneVO;
import com.e180.vo.SinkVO;
import com.e180.vo.SourceVO;
import com.e180.vo.TrafficObjectVO;

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
	void update(double dt, int i,SceneVO myVO){
		
		myMap.updateRoads(dt, i, myVO);
		myMap.updateNodes(dt, i, myVO);

	}
	static SceneVO writeScene(){
		SceneVO sceneOut = new SceneVO();
		Factory myFactory = new Factory();
		Set vehicles = myMap.getVeh();
		Set verts = myMap.getVertices();
		Set edges = myMap.getEdges();
		//if (!vehicles.isEmpty())
		//{myFactory.setVehicles(vehicles,sceneOut);}
		
		myFactory.setNodes(verts,sceneOut);
		myFactory.setRoads(edges,sceneOut);
		return sceneOut;
	}

	
	public static void main(String[] args) {
		//read in the file
		FileParser myParser = new FileParser();
		SceneVO myVO = myParser.readFileIntoScene("C:\\Documents and Settings\\Eric Vacca\\workspace\\E180 Serialization\\src\\input.txt");
		readSceneVO(myVO);
		//create1Road();
		
		System.out.println("Simulation started");
		myVO = writeScene();
		myMap.initializeWeights();
		for (int i =0; i<myCont.getTotalTime(); i++){
			myCont.update(myCont.getStep(), i,myVO);
		}
		System.out.println( myVO.Serialize() );
		myParser.writeSceneVOIntoFile("C:\\output.txt", myVO);
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
		Node v2 = new Sink();
		v2.setX(100); v2.setY(0);
		myMap.addVertex(v2);
		Road r = new Road(v1, v2, 25);
		myMap.addRoad(r);
		//Car v = new Car(v1,);
	}
	public static void testRoadSearch(){
		myCont = new Controller(1000,1);
		myMap = new Map();
		//Node v1 = new Source(1);
		Node v1 = new Source();
		v1.setX(0);v1.setY(0);
		myMap.addVertex(v1);
		Node v2 = new Sink();
		v2.setX(100); v2.setY(0);
		myMap.addVertex(v2);
		Road r = new Road("alcatraz",25,v1, v2);
		myMap.addRoad(r);
		myMap.initializeWeights();
		new Car(v1,v2);
		//new Car(r,.1);
	}

		public static void readSceneVO(SceneVO scene){
			myCont = new Controller(1000,1);
			myMap = new Map();
			Collection sinks = scene.getSinks();
			Collection sources = scene.getSources();
			Collection roads = scene.getRoads();
			Collection nodes = scene.getNodes();
			Collection to = scene.getTrafficObjs();
			//add all the nodes to the map
			Iterator noder = nodes.iterator();
			while (noder.hasNext()){
				NodeVO n = (NodeVO)noder.next();
				Iterator sourcer = sources.iterator();
				//find the node which is this source and add it
				while (sourcer.hasNext()){
					SourceVO s = (SourceVO)sourcer.next();
					if(s.getNodeId().equals(n.getId())){
						Node nn = new Source(s.getNodeId());
						nn.setX(n.getX());
						nn.setY(n.getY());
						myMap.addNode(nn);
					}			
				}
				Iterator sinker = sinks.iterator();
				while (sinker.hasNext()){
					SinkVO s = (SinkVO)sinker.next();
					if(s.getNodeId().equals(n.getId())){
						Node nn = new Sink();
						nn.setX(n.getX());
						nn.setY(n.getY());
						myMap.addNode(nn);
					}			
				}	
				
				Iterator TOer = to.iterator();
				while (TOer.hasNext()){
					TrafficObjectVO s = (TrafficObjectVO)TOer.next();
					if(s.getNodeId().equals(n.getId())){
						Node nn = new trafficController();
						nn.setX(n.getX());
						nn.setY(n.getY());
						((trafficController)nn).setSpeedlimit(s.getSpeedLimit());
						myMap.addNode(nn);
					}			
				}
			}
			
			//add the roads to the map
			Iterator roader = roads.iterator();
			while (roader.hasNext()){
				Iterator noder1 = nodes.iterator();
				RoadVO r = (RoadVO)roader.next();
				Node start=new Node();
				Node dest=new Node();
				while (noder1.hasNext()){
					NodeVO n = (NodeVO)noder1.next();	
					if (r.getFromNodeId().equals(n.getId())){
						start =myMap.getNode(n.getX(), n.getY());
					}
					if (r.getToNodeId().equals(n.getId())){
						dest =myMap.getNode(n.getX(), n.getY());
					}
				}
				Road road = new Road(start,dest,r.getSpeedLimit());
				myMap.addRoad(road);
			}
		}
		
	public static void Roadpathfinding(){
		myCont = new Controller(1000,1);
		myMap = new Map();
		Node v1 = new Source();	
		v1.setX(0);v1.setY(0);
		myMap.addVertex(v1);
		Node v2 = new trafficController();
		((trafficController)v2).setSpeedlimit(25);
		v2.setX(100); v2.setY(0);
		myMap.addVertex(v2);
		Node v3 = new Sink();
		v3.setX(200);v2.setY(0);
		myMap.addVertex(v3);
		Road r = new Road("alcatraz",25,v1, v2);
		Road rr = new Road("shattuck",30,v2,v3);
		myMap.addRoad(r);
		myMap.addRoad(rr);
		myMap.initializeWeights();
		new Car(v1,v3);
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
	public static void miniSim(){
		
		myCont = new Controller(1000,1);
		myMap = new Map();
		
		//Node v1 = new Source(1);
		Node v1 = new Source();
		v1.setX(0);v1.setY(0);
		myMap.addVertex(v1);
		
		Node v2 = new Node();
		v2.setX(0); v2.setY(100);
		myMap.addVertex(v2);
		
		Node v3 = new Node();
		v3.setX(0); v3.setY(200);
		myMap.addVertex(v3);
		
		Node v4 = new Node();
		v4.setX(100); v4.setY(200);
		myMap.addVertex(v4);
		
		Node v5 = new Node();
		v5.setX(100); v5.setY(100);
		myMap.addVertex(v5);
		
		Node v6 = new Node();
		v6.setX(200); v6.setY(0);
		myMap.addVertex(v6);
		
		Node v7 = new Node();
		v7.setX(200); v7.setY(100);
		myMap.addVertex(v7);
		
		Node v8 = new Node();
		v8.setX(200); v8.setY(200);
		myMap.addVertex(v8);
		
		Node v9 = new Node();
		v9.setX(300); v9.setY(200);
		myMap.addVertex(v9);
		
		Node v10 = new Node();
		v10.setX(300); v3.setY(100);
		myMap.addVertex(v10);
		
		Node v11 = new Node();
		v11.setX(300); v11.setY(0);
		myMap.addVertex(v11);
		
		Node v12 = new Sink();
		v12.setX(400); v12.setY(100);
		myMap.addVertex(v12);
		
		Road r1 = new Road("r1",50,v1, v2);
		Road r2 = new Road("r2",50,v2, v3);
		Road r3 = new Road("r3",15,v2, v5);
		Road r4 = new Road("r4",50,v3, v4);
		Road r5 = new Road("r5",50,v4, v5);
		Road r6 = new Road("r6",25,v5, v7);
		Road r7 = new Road("r7",30,v7, v6);
		Road r8 = new Road("r8",30,v7, v8);
		Road r9 = new Road("r9",30,v6, v11);
		Road r10 = new Road("r10",30,v8, v9);
		Road r11 = new Road("r11",15,v11, v10);
		Road r12 = new Road("r12",20,v9, v10);
		Road r13 = new Road("r13",10,v10, v12);
		
		myMap.addRoad(r1);
		myMap.addRoad(r2);
		myMap.addRoad(r3);
		myMap.addRoad(r4);
		myMap.addRoad(r5);
		myMap.addRoad(r6);
		myMap.addRoad(r7);
		myMap.addRoad(r8);
		myMap.addRoad(r9);
		myMap.addRoad(r10);
		myMap.addRoad(r11);
		myMap.addRoad(r12);
		myMap.addRoad(r13);
		myMap.initializeWeights();	
			
		Car c = new Car(v1,v12);
	}
}
