package RandomEnv;

import java.util.concurrent.ThreadLocalRandom;

public class ParallelRandomGenerator implements RandomGenerator {
	
	@Override
	public double nextGaussian(double stDev) {
		return stDev*ThreadLocalRandom.current().nextGaussian();
	}

	@Override
	public double nextGaussian(double mean, double stDev) {
		return stDev*ThreadLocalRandom.current().nextGaussian() + mean;
	}

	@Override
	public double[] correlatedGaussian(double stDev, double rho) {
		double correlated1 = ThreadLocalRandom.current().nextGaussian();
		double normal1 = ThreadLocalRandom.current().nextGaussian();
		double correlated2 = rho*correlated1 + Math.sqrt(1.0 - rho*rho)*normal1;
		return new double[] {correlated1*stDev, correlated2*stDev};
	}

	@Override
	public double nextGamma(double nu, double time) {
		return Double.NaN;
	}

}
