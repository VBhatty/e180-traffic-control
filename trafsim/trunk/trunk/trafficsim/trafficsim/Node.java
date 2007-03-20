package trafficsim;


//import edu.uci.ics.*;
import edu.uci.ics.jung.graph.impl.SparseVertex;
//import org.apache.commons.collections.*;
//import java.awt.Point;
//import java.awt.geom.Point2D;
//import java.util.ArrayList;


public class Node extends SparseVertex{

	public double x;
	public double y;
	public double speedlimit;

	Node (){
		super();
	}
	void setX(double X){
		x = X;
	}
	void setY(double Y){
		y=Y;
	}
	
	void setSpeedlimit(double speedlimit){
		
		this.speedlimit = speedlimit;
	}
	
	public double getSpeedLimit(){
		return speedlimit;
	}
}
