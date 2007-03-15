
import java.util.ArrayList;

public class Controller {
// fields
	ArrayList <Vehicle>Vehicle_list;
	ArrayList  <Road>Roads_list;
	ArrayList <Node>Node_list;
	int totaltime;
	final int dt;
	int current_time;
	boolean[][] mapGrid;
	
	/* 
	 * constructor for simulation controller
	 */
	public Controller(int totTime, int step ){
		totaltime = totTime;
		dt = step;
		current_time = 0;
		Vehicle_list = new ArrayList<Vehicle>();
		Roads_list = new ArrayList<Road>();
		Node_list = new ArrayList<Node>();
	}
	
	double getCurrentTime(){
		return current_time;
	}
	double getStep(){
		return dt;
	}
	double getTotalTime(){
		return totaltime;
	}
	void updateRoad(double dt){
		for (int i=0; i < Roads_list.size(); i++) {
			Road road = (Road)Roads_list.get(i);
			road.updateVehicles(dt);
		}
	}
	
	public int totalVehicles() {
		int total = 0;
		for (int i=0; i < Roads_list.size(); i++) {
			Road road = (Road)Roads_list.get(i);
			total += road.totalVehicles();
		}
		return total;
	}
	
	void addVehicle(Vehicle v){
		Vehicle_list.add(v);

	}
	void addNode(Node n){
		Node_list.add(n);
	}

	public void addRoad(Road r) {
		Roads_list.add(r);
	}
}
