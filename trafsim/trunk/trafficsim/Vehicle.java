
import java.util.ArrayList;
import java.util.Random;

public abstract class Vehicle {

// Class parameters

	//	public static final double mass_mean = 1000; //may be used as distribution mean to generate mass
	public double mass;			//mass of this vehicle
	public double lenght;		//length of this vehicle
	public double cur_speed;	//initial speed at current time step
	public double cur_accel;	//acceleration at current time step, constant through the time step 
	public Road myRoad;		//will be obmitted because this Road is Route[0]
	public double loc_fraction; 	//fraction of current Route[0] that the front of the car has travelled
	public Vehicle car_in_front;	//Nearest vehicle in front of this vehicle at current route at current time

// Some vehicle statistics
	public double average_speed;	//Average speed since creation
	public double distance_travelled;	//distance travelled since creation
	public double time_since_creation;	// time elapsed since creation

	public Node destination;		//Destination of car, generated at timeof creation
	public ArrayList<Road> route;	//ArrayList containing the roads on the route from current road to
									//the road that ends in the destination node in chronological ordering
									

	abstract boolean isNull();
// Constructor	
	public Vehicle(Road r)
	{
		cur_speed=0;
		cur_accel=0;
		loc_fraction=0;
		average_speed=0;
		time_since_creation=0;
		destination=null;
		route = new ArrayList<Road>();
		route.add(r);
		//finding_route();
		//generate_mass_and_length();
		
		
	}
	public void setMyRoad(Road r){

	}
public void generate_mass_and_length()
{
	Random U = new Random();
	double x =  U.nextFloat();
	// probability function depending on subclass 
	//mass=mass_mean = some function of x
	// length function depending on subclass, for cars:
	lenght= 5.773e-4*mass+2.834;
}

public void finding_route()
{
	route.add(myRoad);
}

public double max_acceleration()
{
	double max = 0;
// max= somefunction(cur_speed, friction?, mass)
	return max;
}

public double max_breaking()
{
	double max = 0;
//max = somefunction(cur_speed, route[0].friction, mass)
	return max;
}

public Vehicle find_car_in_front()
{
	//searches through route to find the nearest car in front on the current route.
	//Starting by searching route[0] for the vehicle with the smallest value
	//loc_frac > this.loc_fraq.
	//If no cars fulfils this requirement it searches route[i] while it finds a road with
	//at least one vehicle on it, returning the vehicle with the smallest loc_fraq
	Vehicle somecar = null;
	return somecar;
}

public void check_car_in_front()
{
	//find the vehicle in front and set this as vehicle_in_front if it isn't allready
	Vehicle somecar= find_car_in_front();
	if (car_in_front != somecar)
	{
		car_in_front = somecar;
	}
}

public void update_stat(double dt) //done after cur_accel is found on each step,and before cur_speed
{
	// updates time_since_creation by adding the last timestep
	time_since_creation = time_since_creation + dt;
	//updates distance_travelled by adding the distance travelled the last timestep assuming constant acceleration
	distance_travelled =  distance_travelled + (cur_speed + (cur_speed + cur_accel*dt))*0.5*dt;
	//updates average_speed by divide distance_travelled by time_since_creation
	average_speed= distance_travelled/time_since_creation;
}

public void update_position(double dt )
{
	//distance travelled at current step
	double dp = (cur_speed + (cur_speed + cur_accel*dt))*0.5*dt;
	// adding the distance travelled to the fraction of the current road
	double fraction = loc_fraction + dp/route.get(0).length;
	int count;
	// if the distance travelled is longer than what is left of current road,
	// the vehicle start on the next road on the route
	while ( fraction > 1)
	{
		//calculating how much longer than what is left of the road that the vehicle have
		//travelled in current timestep
		fraction = fraction - 1;
		dp = fraction*route.get(0).length;
		//finding number of vehicles on current road
		count = route.get(0).totalVehicles()-1;
		//removes the last vehicle, which is this one
		route.get(0).vehicles.remove(count);
		
		//some if-sentence here to terminate the car if it has reached the destination
		
		//remove completed road from route
		//route.remove(0);
		
		//add this vehicle to the vehicles-list of the road
		//(route.get(0)).vehicles.add(0,this);
		
		// calculating fraction completed at next road on route
		fraction = dp/route.get(0).length;
	}
	// setting loc_fraction equal the fraction completed at current road
	loc_fraction = fraction;
}
public void update_speed(double dt) //call in the end of timestep as the other updatefunctions use speed at  
									// start of time step as cur_speed
{
	cur_speed = cur_speed + cur_accel*dt;
}


//set the speed to some fixed speed
public void set_speed(double speed){
	cur_speed = speed;
}

//Function to calculate acceleration at current time step, constant acceleration assumed during timestep

public void find_acceleration()
{
	double accel = neccessary_car_in_front_acceleration();
	double a;
	
	Node[] N = nodes_before_full_stop_acceleration();
	
	for (Node i : N)
	{
		a=neccessary_node_in_front_acceleration(i);
		if(accel>a)
		{
			accel = a;
		}
	}
	//here we have to figure out some algorithm to choose acceleration
	//suggestion:
	// cases:
	// 1(emergency)		  if accel is less than max_breaking then acceleration is set equal to max_breaking
	// 2(smooth breaking) if accel is between max_breaking and p*max_breaking with 
	//					  some coefficient pb (0<pb<1) then acceleration is set equal to accel
	// 3(needed acceleration small in magnitude, so acceleration is choosen according to speedlimit)
	//					  if pb*max_breaking < accel < pa*max_acceleration
	// 4(smooth acceleration)
	// 5 (max acceleration)
	
	//maybe some if-sentence should be added so that the vehicle doesn't exceed the speed limit
	//
	// last set cur_accel = accel
}

public double neccessary_car_in_front_acceleration()
{
	Road r;
// neccessary acceleration to get the same speed as the car in front 1 meter 
//	behind the current position of the rearend of the car in front, by constant acceleration
	double nec;
// distance to the car in front
	double dist = 0;
//	 v - the current speed of the car in front
	double v = 0;
//	 f - the current fraction of the road the car in front has completed
	double f;
// If no car in front, there is no reason to accelerate to adapt to the car in front
	if (car_in_front.isNull())
	{
		nec= Float.POSITIVE_INFINITY;
	}
	else
// finding acceleration
	{
// r - the current road of the car in front
	r = car_in_front.route.get(0);
	
// f - the current fraction of the road the car in front has completed
	 f = car_in_front.loc_fraction;
	
// v - the current speed of the car in front
	 v = car_in_front.get_speed();
	}
	//search through route to find distance to the point f (fraction of the road) on r (Road),
	//update dist while searching
	
// using constant acceleration equation to calculate the acceleration needed
	nec = 0.5*(v-cur_speed)*(v+cur_speed)/dist;
	
	return nec;
}
public Node[] nodes_before_full_stop_acceleration()
{
	Node[] N = null;
// Calculate the distance it takes to come to a full stop,
// then finding nodes at the route within this range, returning an array of these nodes
	return N;
	
}
public double neccessary_node_in_front_acceleration(Node n)
{
	double nec = 0;
// similar to neccessary_car_in_front_acceleration
// find the distance to n, and the velocity change needed to get the speed of
// the road at the route that starts at node n, and calculate the acceleration
// needed to obtain this speed by constant acceleration
	return nec;
}

public double get_speed()
{
	return cur_speed;
}

//temporary function to find the acceleration for simple model use
public void set_acceleration()
{
	cur_accel =(route.get(0).getLimit() - this.cur_speed)/6; 
}


public void printSpeed() 
{
	System.out.println(get_speed());
}

}

