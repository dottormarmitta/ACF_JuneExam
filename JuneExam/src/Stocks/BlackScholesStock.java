package Stocks;

import RandomEnv.RandomGenerator;

/**
 * Class representing Black-Scholes model. Implements {@link StockProcess}
 * 
 * @version 1.5
 * @author Guglielmo Del Sarto
 */
public class BlackScholesStock implements StockProcess {

	private double initial;
	private double vol;
	private double rfr;

	public BlackScholesStock() {
		this.initial = 100.0;
		this.vol     = 0.20;
		this.rfr     = 0.01;
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
		return initial*Math.exp((rfr - 0.5*vol*vol)*sqrtMaturity*sqrtMaturity + 
				vol*generator.nextGaussian(sqrtMaturity));
	}

}
