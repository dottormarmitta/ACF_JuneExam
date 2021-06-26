package Options;

/**
 * This interface is an abstraction for a call option
 * PayOff<sub> T </sub> = max(S<sub> T </sub> - K, 0)  
 * 
 * @version 1.5
 * @author Guglielmo Del Sarto
 */
public interface CallOption extends Option {
	
	/**
	 * Set the strike of the option
	 * 
	 * @param K the strike
	 */
	public void setStrike(double K);

}
