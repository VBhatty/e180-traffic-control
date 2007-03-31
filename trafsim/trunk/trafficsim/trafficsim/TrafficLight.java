package trafficsim;

public class TrafficLight extends trafficController{
	// initializing variables
	boolean green;
	boolean yellow;
	boolean red;
	int greenTime;
	int yellowTime;
	int redTime;
	
	public TrafficLight(int GT, int RT){
		this.greenTime = GT;
		this.redTime = RT;
	}
	/*
	 *  given the speed limit of the road feeding into this light, it calculates the minimum yellow
	 *  light time so there is never a car that cannot stop in time and not go through the light
	 *  before it turns red
	 */
	public void setYellow(double speed_limit){
		double stopping_distance = 88/50*(speed_limit)+24/Math.pow(speed_limit/50,2)+74*Math.pow(speed_limit/50,3); // calculates stopping distance based on formulas from physical model ppt
		double time_to_stop = Math.sqrt(stopping_distance*(2*24/Math.pow(50,2)+3*74*speed_limit/Math.pow(50,3))); // gets an upperbound on the time it would take to stop going the speed limit and applying maximum braking
		yellowTime = (int)Math.ceil(time_to_stop); // determines yellowTime by rounding up to nearest integer
		greenTime = greenTime - yellowTime; // since only a green time and red time were specified initially, recalculates green time by subtracting yellow time, red time stays constant
	}
	// returns state of light
	public boolean isGreen(){
		return green;
	}
	// returns state of light
	public boolean isYellow(){
		return yellow;
	}
	// returns state of light
	public boolean isRed(){
		return red;
	}
	// returns object identity
	public boolean isTrafficLight(){
		return true;
	}
}

