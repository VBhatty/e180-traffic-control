package trafficsim;
import java.util.ArrayList;

public class Road {
	
	public String name;
	public ArrayList<Vehicle> vehicles;	//list of current vehicles on the road
	public double coeff_of_friq;	//the current coefficient of friction of the road
	public double length;	//the length of the road
	public Node start_node; //the node at the start of the road
	public Node end_node; //the node at the end of the road
	public double speed_limit; //speed limit of the road
	public int width;	//number of lanes of the road
	public double average_speed; // average speed of the current vehicles on the road
	public double average_weight;	//average weight of the current vehicles on the road
	
	

}

