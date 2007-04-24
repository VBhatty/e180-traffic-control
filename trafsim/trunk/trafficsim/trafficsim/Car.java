package trafficsim;

import java.util.Random;


/**
 * The car class extends vehicles with a specified weight.   
 * @author Eric Vacca
 *
 */
public class Car extends Vehicle {

	public Car(Road r,double perc) {
		super(r,perc);
		
		
	}
	public Car(Node start,Node end,int spawn) {
		super(start,end,spawn);
	}

	@Override
	boolean isNull() {
		// TODO Auto-generated method stub
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
		mass = 1447+z*156.15;
		
		// length as function of mass:
		length= 5.773e-4*mass+2.834;
	}

}
