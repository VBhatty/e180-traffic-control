//import edu.uci.ics.jung.graph.Edge;
//import edu.uci.ics.jung.graph.Vertex;
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
	
}
