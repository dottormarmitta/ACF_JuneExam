package Options;

/**
 * This interface is an abstraction for an option
 * 
 * @version 1.0
 * @author Guglielmo Del Sarto
 */
public interface Option {
	
	/**
	 * Perform MonteCarlo simulations, stored internally
	 * 
	 * @param maturity T
	 * @param discountFactor P(0, T)
	 * @param numberOfSimulations to perform
	 * @param seeds of randomGenerator
	 * @param randomType either "mt" for Mersenne of "lcg"
	 */
	public void simulate(double maturity, double discountFactor,
			int numberOfSimulations, long seeds, String randomType);
	
	/**
	 * Get MonteCarloValue of the option
	 * 
	 * @return option value (using monteCarlo method)
	 * @throws Exception if simulation is not yet performed
	 */
	public double getMonteCarloValue() throws Exception;
	
	/**
	 * Get monteCarloError
	 * 
	 * @param expectedValue the mean
	 * @return monteCarloError
	 * @throws Exception if simulation is not yet performed
	 */
	public double getMonteCarloError(double expectedValue) throws Exception;

}
