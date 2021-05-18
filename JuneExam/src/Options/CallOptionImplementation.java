package Options;

import LinearAlgebra.Statistic;
import RandomEnv.BasicRandomGenerator;
import RandomEnv.MersenneRandomGenerator;
import RandomEnv.RandomGenerator;
import Stocks.StockProcess;

public class CallOptionImplementation implements CallOption {
	
	private double[] simulations;
	private StockProcess currentStock;
	private double K;
	private RandomGenerator generator;

	public CallOptionImplementation(StockProcess stock) {
		this.currentStock = stock;
	}

	@Override
	public void simulate(double maturity, double discountFactor, 
			int numberOfSimulations, long seed, String randomType) {
		simulations  = new double[numberOfSimulations];
		if(randomType == "lcg") {
			generator = new BasicRandomGenerator(seed);
		} else {
			generator = new MersenneRandomGenerator(seed);
		}
		double sqrtMaturity = Math.sqrt(maturity);
		for (int w = 0; w < numberOfSimulations; w++) {
			simulations[w] = 
					Math.max(-K + currentStock.getValue(sqrtMaturity, generator), 0.0)*discountFactor;
		}
	}

	@Override
	public double getMonteCarloValue() throws Exception {
		if (simulations == null) {
			throw new Exception("Simulation not performed. Please call the method simulate()");
		}
		return Statistic.getAverage(simulations);
	}

	@Override
	public double getMonteCarloError(double expectedValue) throws Exception {
		if (simulations == null) {
			throw new Exception("Simulation not performed. Please call the method simulate()");
		}
		return 3.0*Math.sqrt(Statistic.getVariance(simulations, expectedValue)/simulations.length);
	}

	@Override
	public void setStrike(double K) {
		this.K = K;
	}

}
