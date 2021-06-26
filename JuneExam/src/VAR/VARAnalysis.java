package VAR;

import java.io.IOException;

/**
 * This interface is an abstraction for a VAR analysis
 * on a generic financial product
 * 
 * @version 2.0
 * @author Guglielmo Del Sarto
 */
public interface VARAnalysis {
	
	/**
	 * This method simulate the process. Stored internally
	 * 
	 * @param benchmark the product benchmark value
	 * @param riskFree the risk free rate
	 * @param BSvol the market vol
	 * @param seed the random generation
	 */
	public void simulate(double benchmark, double riskFree, double BSvol, 
			int seed);
	
	/**
	 * This method stores P&L array in a text file, ready to be
	 * printed out
	 * 
	 * @throws IOException
	 */
	public void printHistogram() throws IOException;
	
	/**
	 * This method returns the VAR value given a percentage
	 * 
	 * @param percentage the VAR value (e.g. 95%)
	 * @return VAR the value at risk for given percentage
	 */
	public double getVarFromPercent(double percentage);

}
