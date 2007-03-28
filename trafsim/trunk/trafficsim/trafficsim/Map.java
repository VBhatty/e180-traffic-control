package trafficsim;

//import edu.uci.ics.jung.graph.Edge;
//import edu.uci.ics.jung.graph.Vertex;
import java.util.Iterator;
import java.util.Set;

import edu.uci.ics.jung.graph.ArchetypeEdge;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValue;
import edu.uci.ics.jung.graph.decorators.UserDatumNumberEdgeValue;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;


public class Map extends DirectedSparseGraph{
	UserDatumNumberEdgeValue weight;
	public Road addRoad(Road arg0) {
		// TODO Auto-generated method stub
		super.addEdge(arg0);
		return arg0;
	}
	public Node addNode(Node arg0) {
		// TODO Auto-generated method stub
		super.addVertex(arg0);
		return arg0;
	}
	
	void updateNodes(double dt){
		Set<Node> myNodes = this.getVertices();
		Iterator no = myNodes.iterator();
		
		while(no.hasNext()){
			Node nn = (Node)no.next();
			if (nn.isSource()){
				((Source)nn).generate_new_car();
			}
			if (nn.isTrafCont()){
				((trafficController)nn).updateTrafCont(dt);
			}
			if (nn.isSink()){
				//nothing to do here yet
			}
		}
	}
	/*
	 * calculates the weight of the road in its current condition
	 * 
	 */
	void setWeight(Road r){
		double avgVel = r.getAvgSpeed2();
		double speedLimit = r.getLimit();
		if (avgVel==0){
			avgVel = speedLimit;
		}
		double leng = r.getLength();
		Number weight = leng/avgVel;
		((NumberEdgeValue) weight).setNumber(((ArchetypeEdge)this),weight);
	}
	
	NumberEdgeValue getWeight(){
		return weight;
	}

	
	/*
	 * iterates through the roads and updates all the cars acceleration, position
	 * and velocity on them
	 */
	void updateRoads(double dt){
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
			//rr.updateVehiclesAcceleration(dt);
			//rr.updateVehiclesPosition(dt);
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
}
