package Bonds;

/**
 * This interface is an abstraction for a bond curve
 * P(0, T)
 * 
 * @version 1.0
 * @author Guglielmo Del Sarto
 */
public interface BondCurve {
	
	/**
	 * Get bond value P(0, T)
	 * 
	 * @param maturity T
	 * @return P(0, T)
	 */
	public double getBond(double maturity);
	
	/**
	 * Get bond value P(t, T)
	 * 
	 * @param initial t
	 * @param maturity T
	 * @return P(t, T)
	 */
	public double getBond(double initial, double maturity);
	
}
