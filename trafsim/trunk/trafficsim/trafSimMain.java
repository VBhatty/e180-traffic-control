

//import java.awt.Point;
//import java.awt.geom.Point2D;

//import edu.uci.ics.jung.graph.Vertex;

public class trafSimMain {
	
	public static void main(String[] args) {
		

		double dt=1;
		double tend=100;
		
		Source v1 = new Source();
		v1.setX(0);
		v1.setY(0);
		//dummy variable
		int generate = 0;
		
		
		Node v2 = new Node();
		v2.setX(500);
		v2.setY(0);
		
		Node v3 = new Node();
		v3.setX(1000);
		v3.setY(0);
		
		Road r1 = new Road(0,"alcatraz",500,25,1,v1, v2);
		Road r2 = new Road(1,"cosmo street",500,40,1,v2, v3);
		
		Car c1 = new Car(r1,0.0);
		Car c2 = new Car(r1,0.0);
		Car c3 = new Car(r1,0.0);
		Car c4 = new Car(r1,0.0);
		Car c5 = new Car(r1,0.0);
		Car[] cars = {c1,c2,c3,c4,c5};
		int count =0;
		
		
		double nextStart = 5;
		
		for (double time = 0; time < tend; time=time+dt){
			
			
			
			//if generate is 0, then a car just left the source, and a new car
			//will be generated at time nextStart
			if (generate == 0){
				nextStart = time + (double)v1.generate_next_car(time);
				generate =1;
			}
			else if (time >= nextStart){
				//generate vehicle
				r1.addVehicle(cars[count]);
				count++;
				generate = 0;
			}
			
			r1.updateVehicles(dt);
			r2.updateVehicles(dt);
			
		}
	}
	
	
}
