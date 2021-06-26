package Stocks;

import RandomEnv.RandomGenerator;

/**
 * This interface is an abstraction for stock
 * one step simulation (i.e. from 0 to T)
 * <br>
 * S <sub> 0 </sub> &rarr; S <sub> T </sub>
 * 
 * @version 1.0
 * @author Guglielmo Del Sarto
 */
public interface StockProcess {
	
	/**
	 * Set stock initial value S <sub> 0 </sub>
	 * 
	 * @param initialValue of the stock
	 */
	public void setInitialValue(double initialValue);
	
	/**
	 * Set stock volatility
	 * 
	 * @param vol of the stock expressed as stDev
	 */
	public void setVol(double vol);
	
	/**
	 * Set riskFree
	 * 
	 * @param rfr of the model (used as drift)
	 */
	public void setRiskFreeRate(double rfr);
	
	/**
	 * Get stock value S <sub> T </sub>
	 * 
	 * @param sqrtMaturity sqrt(T)
	 * @param generator a RandomGenerator
	 * @return S <sub> maturity </sub>
	 */
	public double getValue(double sqrtMaturity, RandomGenerator generator);
	
	/**
	 * Get stock volatility
	 * 
	 * @return volatility of the stock
	 */
	public double getVol();
	
	/**
	 * Get the initial stock value
	 * 
	 * @return s0 the initial stock value
	 */
	public double getInitial();

}
