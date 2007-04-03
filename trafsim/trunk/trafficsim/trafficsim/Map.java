package trafficsim;

//import edu.uci.ics.jung.graph.Edge;
//import edu.uci.ics.jung.graph.Vertex;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import edu.uci.ics.jung.graph.ArchetypeEdge;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValue;
import edu.uci.ics.jung.graph.decorators.UserDatumNumberEdgeValue;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;


public class Map extends DirectedSparseGraph{
	UserDatumNumberEdgeValue weight;
	public Road addRoad(Road arg0) {
		weight = new UserDatumNumberEdgeValue(arg0);
		super.addEdge(arg0);
		return arg0;
	}
	public Node addNode(Node arg0) {
		super.addVertex(arg0);
		return arg0;
	}
	
	void updateNodes(double dt, int i){
		Set<Node> myNodes = this.getVertices();
		Iterator no = myNodes.iterator();
		
		while(no.hasNext()){
			Node nn = (Node)no.next();
			if (nn.isSource()){
				//((Source)nn).generate_new_car(i);
			}
			if (nn.isTrafCont()){
				((trafficController)nn).updateTrafCont(dt);
			}
		}
	}
	/**
	 * calculates the weight of the road in its current condition
	 */
	void setWeight(Road r){
		double avgVel = r.getAvgSpeed2();
		double speedLimit = r.getLimit();
		if (((Double)avgVel).isNaN()){
			avgVel = speedLimit;
		}
		double leng = r.getLength();
		Number w = leng/avgVel;
		weight.setNumber(r,w);
	}
	
	NumberEdgeValue getWeight(){
		return weight;
	}

	
	/*
	 * iterates through the roads and updates all the cars acceleration, position
	 * and velocity on them
	 */
	void updateRoads(double dt, int i){
		Set<Road> myRoads = this.getEdges();
		
		//updating all the vehicles is done in two step so that the acceleration is
		//not calculated based on the position of the car in two different timesteps
		
		// going through all the roads and for each updating the acceleration of
		// the vehicles at each road

		Iterator ro1 = myRoads.iterator();
		while(ro1.hasNext()){
			Road rr = (Road)ro1.next();
			setWeight(rr);
			rr.updateVehicles(dt);
		}
	
			Iterator ro2 = myRoads.iterator();
			while(ro2.hasNext()){
				Road rrr = (Road)ro2.next();
				setWeight(rrr);
				rrr.updatePosition(dt);
		}
	}
	public int totalVehicles() {
		int total = 0;
		Set<Road> myRoads = this.getEdges(); 
		Iterator ro = myRoads.iterator();
		while (ro.hasNext()){
			Road rr =(Road) ro.next();
			total += rr.totalVehicles();
		}
		return total;
	}
	
	public void initializeWeights(){
		Set<Road> myRoads = this.getEdges();
		Iterator ro1 = myRoads.iterator();
		while(ro1.hasNext()){
			Road rr = (Road)ro1.next();
			setWeight(rr);
		}
	}
	public Set getSinks() {
		// TODO Auto-generated method stub
		Set allNodes=  super.getVertices();
		Set<Sink> sinks = new HashSet<Sink>();
		Iterator nodes = allNodes.iterator();
		while (nodes.hasNext()){
			Node myNode = (Node)nodes.next();
			if (myNode.isSink()){
				sinks.add((Sink)myNode);
			}
		}
		return sinks;
	}
	Sink getRandomSink(){
		Sink s = new Sink();
		Set sinks = getSinks();
		Random U = new Random();
		int size =sinks.size();
		
		Iterator iter = sinks.iterator();
		if (size==0){
			s = (Sink)iter.next();
		}else{
			int rand = U.nextInt(size);
		while (rand!=0){
			
			s = (Sink)iter.next();
			rand -=1;
		}
		}
		return s;
	}
}
