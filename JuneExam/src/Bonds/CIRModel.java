package Bonds;

import RandomEnv.RandomGenerator;

/**
 * Class representing CIR model
 * 
 * @version 1.5
 * @author Guglielmo Del Sarto
 */
public class CIRModel {

	private double k, eta, theta,  sqrtTime, initial;

	public CIRModel(double k, double theta,
			double eta, double sqrtTime, double initial) {
		this.k = k;
		this.theta = theta;
		this.eta = eta;
		this.sqrtTime = sqrtTime;
		this.initial = initial;
	}
	
	/**
	 * Return integral and final value of a CIR process
	 * 
	 * @param ns the number of subdivisions of [0, T]
	 * @param generator a RandomGenerator
	 * @return array [terminalVol, integralVol]
	 */
	public double[] getIntegralVol(int ns, RandomGenerator generator) {
		double integral = 0.0;
		double prevVol = initial;
		double currentVol = 0.0;
		double correlated2;
		double t = sqrtTime*sqrtTime;
		double dt = (double) t/ns;
		for (int i = 1; i <= ns; i++) {
			correlated2 = generator.nextGaussian(1.0);
			currentVol = prevVol + k*(theta - prevVol)*dt + eta*Math.sqrt(prevVol*dt)*correlated2;
			integral += dt*((currentVol+prevVol)*0.5);
			prevVol = currentVol;
		}
		return new double[] {currentVol, integral};
	}


}
