package Options;

/**
 * This interface is an abstraction for a put option
 * PayOff<sub> T </sub> = max(K - S<sub> T </sub>, 0)  
 * 
 * @version 1.0
 * @author Guglielmo Del Sarto
 */
public interface PutOption extends Option {
	
	/**
	 * Set the strike of the option
	 * 
	 * @param K the strike
	 */
	public void setStrike(double K);

}
