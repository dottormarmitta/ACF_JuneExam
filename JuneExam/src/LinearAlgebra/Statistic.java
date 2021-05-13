package LinearAlgebra;

import java.util.stream.DoubleStream;

/**
 * Basic statistics tool needed for MC
 * <br>
 * Parallel implementation
 * 
 * @version 1.0
 * @author Guglielmo Del Sarto
 */
public class Statistic {
	
	/**
	 * Get Kahan mean of a sample vector
	 * 
	 * @param values the sample
	 * @return average
	 */
	public static double getAverage(double[] values) {
		return DoubleStream.of(values).parallel().sum()/values.length;
	}
	
	/**
	 * Get Kahan standard error (not unbiased
	 * as it is divided by n and not n-1)
	 * 
	 * @param values the sample
	 * @param mean of the sample
	 * @return standard error
	 */
	public static double getVariance(double[] values, double mean) {
		return DoubleStream.of(values).parallel().map(x -> Math.pow(x - mean, 2))
				.sum()/values.length;
	}
	
	/**
	 * Check whether:
	 * <br>
	 * lowerBound &#8804; number &#8804; upperBound
	 * 
	 * @param number of interest
	 * @param lowerBound of CI
	 * @param upperBound of CI
	 * @return number is in interval?
	 */
	public static boolean isInside(double number, double lowerBound,
			double upperBound) {
		if(number >= lowerBound && number <= upperBound) {
			return true;
		}
		return false;
	}

	public static boolean isInside(double number, double lowerBound,
			double upperBound, double discrepa) {
		double a = number - discrepa;
		double b = number + discrepa;
		if(a <= lowerBound && lowerBound <= b) {
			return true;
		} else if (lowerBound <= a && a <= upperBound) {
			return true;
		}
		return false;
	}
	
}
