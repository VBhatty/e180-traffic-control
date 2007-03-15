package trafficsim;

import java.awt.Point;
import java.awt.geom.Point2D;

public class trafSimMain {
	
	public static void main(String[] args) {
		create1Road();
		Controller myCont = new Controller(100,1);
		myCont.addRoad(r);
		
		for (int i = myCont.current_time; i<myCont.getTotalTime(); i++){
			myCont.updateRoad(myCont.getStep());
		}

	}
	
	static Road r;
	/**
	 * @param args
	 */
	
	public static void create1Road(){
		Point2D nodeLoc = new Point(0,0);
		Point2D nodeLoc2 = new Point(100,0);
		Node myNode1 = new Node(nodeLoc,"one");
		Node myNode2 = new Node(nodeLoc2,"two");
		r = new Road(0,"alcatraz",100,30,1,myNode1,myNode2);
		myNode1.addRoad(r);
		myNode2.addRoad(r);
	}
}
