package trafficsim;
import java.util.ArrayList;

public class Controller {
// fields
	ArrayList Vehicle_list;
	ArrayList  Roads_list;
	ArrayList Node_list;
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
	
	void updateRoad(){
		for (int i=0; i < Roads_list.size(); i++) {
			road = Roads_list.get(i);
			road.updateVehicles();
		}
	}
	
	public int totalVehicles() {
		total = 0;
		for (int i=0; i < Roads_list.size(); i++) {
			road = Roads_list.get(i);
			total += road.totalVehicles();
		}
		return total;
	}
	
	void addVehicle(Vehicle v){
		Vehicle_list.add(v);

	}
	void addRoad(Road r){
		
	}
	void addNode(Node n){
		
	}
}
