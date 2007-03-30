package trafficsim;

import java.util.ArrayList;

public class multiLaneRoad extends Road{
	private ArrayList <Road>myRoads;

	public multiLaneRoad(String Name, double length, double speed_limit, int width, Node start, Node end) {
		super(Name, length, speed_limit, width, start, end);
		// TODO Auto-generated constructor stub
	}

}
