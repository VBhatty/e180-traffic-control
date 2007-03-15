package trafficsim;

import java.awt.geom.Point2D;
import java.util.ArrayList;
public class Node {

	public String name;
	public Point2D start_location;
	public ArrayList<Road> roads;
	Node (Point2D location,String n){
		start_location = location;
		name = n;
	}
	void addRoad(Road r){
		roads.add(r);
	}
	void sortRoads(){
		
	}
}
