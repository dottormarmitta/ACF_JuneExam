package RandomEnv;

import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.random.MersenneTwister;

/**
 * Class implementing {@link RandomGenerator}
 * <br>
 * WARNING: it is not thread safe. Should not be used
 * in concurrent programs
 * 
 * @version 1.7
 * @author Guglielmo Del Sarto
 */
public class MersenneRandomGenerator implements RandomGenerator {
	
	private MersenneTwister localGenerator;

	public MersenneRandomGenerator(long seed) {
		this.localGenerator = new MersenneTwister(seed);
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
