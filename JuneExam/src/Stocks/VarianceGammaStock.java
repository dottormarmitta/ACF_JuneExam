package Stocks;

import RandomEnv.RandomGenerator;

/**
 * Class representing Variance Gamma model. Implements {@link StockProcess}
 * 
 * @version 1.5
 * @author Guglielmo Del Sarto
 */
public class VarianceGammaStock implements StockProcess {

	private double initial;
	private double vol;
	private double rfr;
	private double th;
	private double nu;
	private double omega;
	
	public VarianceGammaStock(double theta, double nu) {
		this.th = theta;
		this.nu = nu;
	}

	@Override
	public void setInitialValue(double initialValue) {
		this.initial = initialValue;
	}

	@Override
	public void setVol(double vol) {
		this.vol = vol;
		this.omega = (Math.log(1 - th*nu - vol*vol*nu*0.5))/nu;
	}

	@Override
	public void setRiskFreeRate(double rfr) {
		this.rfr = rfr;
	}

	@Override
	public double getValue(double sqrtMaturity, RandomGenerator generator) {
		double currentGamma = generator.nextGamma(nu, sqrtMaturity*sqrtMaturity);
		double currentNorm  = generator.nextGaussian(1.0);
		double x = this.th*currentGamma + this.vol*Math.sqrt(currentGamma)*currentNorm;
		return initial*Math.exp((rfr + omega)*sqrtMaturity*sqrtMaturity + x);
	}

	@Override
	public double getVol() {
		return this.vol;
	}

	@Override
	public double getInitial() {
		return this.initial;
	}

}
