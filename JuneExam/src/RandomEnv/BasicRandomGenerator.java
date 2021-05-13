package RandomEnv;

import java.util.Random;

import org.apache.commons.math3.distribution.GammaDistribution;

/**
 * Class implementing {@link RandomGenerator}
 * <br>
 * WARNING: it is not thread safe. Should not be used
 * in concurrent programs
 * 
 * @version 1.5
 * @author Guglielmo Del Sarto
 */
public class BasicRandomGenerator implements RandomGenerator {
	
	private final Random localGenerator;

	public BasicRandomGenerator(long seed) {
		this.localGenerator = new Random(seed);
	}

	@Override
	public double nextGaussian(double stDev) {
		return stDev*localGenerator.nextGaussian();
	}

	@Override
	public double nextGaussian(double mean, double stDev) {
		return mean + stDev*localGenerator.nextGaussian();
	}
	
	@Override
	public double[] correlatedGaussian(double stDev, double rho) {
		double correlated1 = localGenerator.nextGaussian();
		double normal1 = localGenerator.nextGaussian();
		double correlated2 = rho*correlated1 + Math.sqrt(1.0 - rho*rho)*normal1;
		return new double[] {correlated1*stDev, correlated2*stDev};
	}

	@Override
	public double nextGamma(double nu, double time) {
		return new GammaDistribution(time/nu, nu).sample();
	}

}
