package trafficsim;


//import edu.uci.ics.*;
import edu.uci.ics.jung.graph.impl.SparseVertex;
//import org.apache.commons.collections.*;
//import java.awt.Point;
//import java.awt.geom.Point2D;
//import java.util.ArrayList;
import java.util.UUID;

public class Node extends SparseVertex{

	private double x;
	private double y;
	
	public UUID id;

	Node (){
		super();
		id = UUID.randomUUID();
	}
	
	public String getID() {
		return this.id.toString();
	}
	void setX(double X){
		x = X;
	}
	void setY(double Y){
		y=Y;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	boolean isSource(){
		return false;
	}
	boolean isTrafCont(){
		return false;
	}
	boolean isSink(){
		return false;
	}

}
