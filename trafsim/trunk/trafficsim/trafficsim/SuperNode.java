package trafficsim;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * a Node that internally keeps a directed map of 
 * each incoming and outgoing edge
 * @author Eric Vacca
 *
 */
public class SuperNode extends Node{
	Map interMap;
	SuperNode (){
		super();
		id = UUID.randomUUID();
		interMap = new Map();
//		make a node for each outgoing edge
		Set outEdges = this.getOutEdges();
		Iterator outEd = outEdges.iterator();
		while (outEd.hasNext()){
			Node n = new InternalNode((Road)outEd.next());
			interMap.addNode(n);
			Set inEdges = this.getInEdges();
			Iterator inEd = inEdges.iterator();
//			make a node for each incoming edge and connect
			while (inEd.hasNext()){
				Node n1= new InternalNode((Road)inEd.next());
				interMap.addNode(n1);
				interMap.addRoad(new InternalRoad((InternalNode)n1,(InternalNode)n,4,true));
			}
		}
	}
	public Map getInterMap() {
		return interMap;
	}
	/**
	 * finds and returns the internal node whose reference
	 * is to param road r
	 * @param r
	 * @return
	 */
	InternalNode getInternalNode(Road r){
		Set verts = getInterMap().getVertices();
		Iterator Nodes = verts.iterator();
		while(Nodes.hasNext()){
			InternalNode n = (InternalNode)Nodes.next();
			if (n.getMyRef().equals(r)){
				return n;
			}
		}
		return null;
	}
	
}
