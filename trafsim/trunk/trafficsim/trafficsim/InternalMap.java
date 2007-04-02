package trafficsim;

import java.util.Iterator;
import java.util.Set;

import edu.uci.ics.jung.graph.decorators.NumberEdgeValue;

public class InternalMap extends Map{
	
	void updateNodes(double dt){
		Set<InternalNode> myNodes = this.getVertices();
		Iterator no = myNodes.iterator();
		
		while(no.hasNext()){
			InternalNode nn = (InternalNode)no.next();
			InternalRoad r = nn.getOutEgde();
			if (r.on){
				Vehicle V = nn.getNextVehicle();
				r.addVehicle(V);
			}
		}
	}
	/**
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
			//setWeight(rr);
			rr.updateVehicles(dt);
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
}
