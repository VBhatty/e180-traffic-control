package trafficsim;

import java.util.ArrayList;

public class multiLaneRoad extends Road{
	private ArrayList <Road>myRoads;

	public multiLaneRoad(int id, String Name, double length, double speed_limit, int width, Node start, Node end) {
		super(id, Name, length, speed_limit, width, start, end);
		// TODO Auto-generated constructor stub
	}

}
