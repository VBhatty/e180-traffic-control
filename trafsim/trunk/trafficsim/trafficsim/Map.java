package trafficsim;

//import edu.uci.ics.jung.graph.Edge;
//import edu.uci.ics.jung.graph.Vertex;
import java.util.Iterator;
import java.util.Set;

import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;


public class Map extends DirectedSparseGraph{
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
	
	void updateSources(){
		Set<Node> myNodes = this.getVertices();
		Iterator no = myNodes.iterator();
		
		while(no.hasNext()){
			Node nn = (Node)no.next();
			if (nn.isSource()){
				((Source)nn).generate_new_car();
			}
		}
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
			rr.updateVehicles(dt);
			//rr.updateVehiclesAcceleration(dt);
			//rr.updateVehiclesPosition(dt);
		}
	}
	
}
