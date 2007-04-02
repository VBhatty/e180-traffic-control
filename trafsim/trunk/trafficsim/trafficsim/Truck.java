package trafficsim;

import java.util.Random;



public class Truck extends Vehicle{

	public Truck(Road r,double fract) {
		super(r,fract);
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean isNull() {
		return false;
	}
	public int compareTo(Object arg) {
		int ret=0;
		if (this.equals((Vehicle)arg)){
			ret= 0;
		} else if (this.getPercent()< ((Vehicle)arg).getPercent()){
			ret= -1;
		}else if (this.getPercent()> ((Vehicle)arg).getPercent()){
			ret= 1;
		}
		return ret;
	}
	public void generate_mass_and_length()
	{
		Random U = new Random();
		double z =  U.nextGaussian();
		
		//generating the mass
		//normally distributed with mean 1447 kg and std 156.15 kg
		mass = 2027.25+z*267.75;
		
		// length as function of mass:
		
		length= 3.50e-4*mass+3.564;
	}
}
