package trafficsim;
import java.util.ArrayList;

public class Controller {
// fields
	ArrayList<Vehicle> Vehicle_list;
	ArrayList<Road>  Roads_list;
	ArrayList<Node> Node_list;
	double totaltime;
	final double dt;
	double current_time;
	boolean[][] mapGrid;
	
	/* 
	 * constructor for simulation controller
	 */
	public Controller(double totTime, double step ){
		totaltime = totTime;
		dt = step;
		current_time = 0;
		Vehicle_list = new ArrayList();
		Roads_list = new ArrayList();
		Node_list = new ArrayList();
	}
	
	void addVehicle(Vehicle v){
		Vehicle_list.add(v);

	}
	void addRoad(Road r){
		
	}
	void addNode(Node n){
		
	}
}
