package Stocks;

import Bonds.CIRModel;
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
	private int vt;
	
	public HestonStock(double rho, double k, double theta, double eta, int volRefinement) throws Exception {
		if(2*k*theta - eta*eta <= 0.0) {
			throw new Exception("Feller condition is not met");
		}
		this.rho = rho;
		this.k = k;
		this.theta = theta;
		this.eta = eta;
		this.vt = volRefinement;
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
		double correlated1 = generator.nextGaussian(1.0);
		//double correlated2 = rho*correlated1 + Math.sqrt(1.0 - rho*rho)*generator.nextGaussian(1.0);
		//double newVar = vol + k*(theta - vol)*t + eta*Math.sqrt(vol*t)*correlated2;
		double[] intVar = (new CIRModel(rho, k, theta, eta, sqrtMaturity, vol).getIntegralVol(vt, generator));
		//double intVar = (vol + newVar)*0.5*t;
		double x = -0.5*intVar[1] + rho/eta*(intVar[0] - vol - k*theta*t + k*intVar[1]) + Math.sqrt((1.0 - rho*rho)*intVar[1])*correlated1;
		return initial*Math.exp(rfr*t + x);
	}

}
