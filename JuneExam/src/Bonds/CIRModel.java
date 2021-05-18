package Bonds;

import RandomEnv.RandomGenerator;

public class CIRModel {

	private double rho, k, eta, theta,  sqrtTime, initial;

	public CIRModel(double rho, double k, double theta,
			double eta, double sqrtTime, double initial) {
		this.k = k;
		this.theta = theta;
		this.eta = eta;
		this.rho = rho;
		this.sqrtTime = sqrtTime;
		this.initial = initial;
	}

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
			/*
			System.out.println("i....: " + i);
			System.out.println("dt...: " + dt);
			System.out.println("cT...: " + i*dt);
			System.out.println();
			*/
		}
		return new double[] {currentVol, integral};
	}


}
