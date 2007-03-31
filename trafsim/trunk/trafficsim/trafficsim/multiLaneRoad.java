package trafficsim;

import java.util.ArrayList;

public class multiLaneRoad extends Road{
	private ArrayList <Road>myRoads;
	private int width;

	public multiLaneRoad(String Name, double speed_limit, Node start, Node end) {
		super(Name, speed_limit, start, end);
		// TODO Auto-generated constructor stub
	}
	public multiLaneRoad(String Name, double speed_limit, int w, Node start, Node end) {
		super(Name, speed_limit, start, end);
		width = w;
		// TODO Auto-generated constructor stub
	}
}
