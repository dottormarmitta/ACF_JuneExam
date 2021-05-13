package RandomEnv;

/**
 * Interface representing a customized random generator
 * <br>
 * It can handle the generation of Gaussian, correlated Gaussian
 * and Gamma distributed random variables
 * 
 * @version 1.0
 * @author Guglielmo Del Sarto
 */
public interface RandomGenerator {
	
	/**
	 * Return the next Gaussian-distributed r.v.
	 * <br>
	 * Z &sim; N(0, variance)
	 * <br>
	 * where variance = standardDev <sup> 2 </sup>
	 * 
	 * @param stDev the standard deviation
	 * @return Z
	 */
	public double nextGaussian(double stDev);
	
	/**
	 * Return the next Gaussian-distributed r.v.
	 * <br>
	 * Z &sim; N(0, variance)
	 * <br>
	 * where variance = standardDev <sup> 2 </sup>
	 * 
	 * @param stDev the standard deviation
	 * @param mean of the r.v.
	 * @return Z
	 */
	public double nextGaussian(double mean, double stDev);
	
	/**
	 * Return two correlated Gaussian-distributed r.v.
	 * <br>
	 * Z &sim; N(0, variance)
	 * <br>
	 * where variance = standardDev <sup> 2 </sup>
	 * 
	 * @param stDev the standard deviation
	 * @param rho the correlation coefficient
	 * @return Z
	 */
	public double[] correlatedGaussian(double stDev, double rho);
	
	/**
	 * Return a gamma distributed r.v.
	 * <br>
	 * shape = time/nu
	 * <br>
	 * scale = nu
	 * 
	 * @param nu scale param
	 * @param time of obs
	 * @return randomVariable distributed as G(time/nu, nu)
	 */
	public double nextGamma(double nu, double time);
}
