package Stocks;

import RandomEnv.RandomGenerator;

/**
 * Class representing Heston model. Implements {@link StockProcess}
 * 
 * @version 1.5
 * @author Guglielmo Del Sarto
 */
public class HestonStock implements StockProcess {

	private double initial;
	private double vol;
	private double rfr;
	private double rho;
	private double k;
	private double theta; // mean vol
	private double eta;
	
	public HestonStock(double rho, double k, double theta, double eta) {
		this.rho = rho;
		this.k = k;
		this.theta = theta;
		this.eta = eta;
	}
	
	@Override
	public void setInitialValue(double initialValue) {
		this.initial = initialValue;
	}

	@Override
	public void setVol(double vol) {
		this.vol = vol;
	}

	@Override
	public void setRiskFreeRate(double rfr) {
		this.rfr = rfr;
	}
	
	@Override
	public double getValue(double sqrtMaturity, RandomGenerator generator) {
		double t = sqrtMaturity*sqrtMaturity;
		double[] correlated = generator.correlatedGaussian(sqrtMaturity, rho);
		double variance = vol + k*(theta - vol)*t + eta*Math.sqrt(vol*t)*correlated[1];
		double intVar   = 0.5*(vol+variance);
		double x = (rfr-0.5*intVar)*t + Math.sqrt(intVar)*correlated[0];
		return initial*Math.exp(x);
	}

}
