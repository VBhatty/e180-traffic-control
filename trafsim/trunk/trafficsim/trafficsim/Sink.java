package trafficsim;

import java.util.UUID;

public class Sink extends Node{
	
	private UUID sinkID;
	
	public Sink () {
		super();
		sinkID = UUID.randomUUID();
	}
	
	public String getID() {
		return this.sinkID.toString();
	}
	
	public String getNodeID() {
		return super.getID();
	}

}
