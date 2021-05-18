package Options;

import java.util.stream.IntStream;

import LinearAlgebra.Statistic;
import RandomEnv.ParallelRandomGenerator;
import RandomEnv.RandomGenerator;
import Stocks.StockProcess;

public class PutOptionParallel implements PutOption {
	private double[] simulations;
	private StockProcess currentStock;
	private double K;
	private RandomGenerator generator;

	public PutOptionParallel(StockProcess stock) {
		this.currentStock = stock;
	}

	@Override
	public void simulate(double maturity, double discountFactor, 
			int numberOfSimulations, long seed, String randomType) {
		simulations  = new double[numberOfSimulations];
		generator    = new ParallelRandomGenerator();
		double sqrtMaturity = Math.sqrt(maturity);
		
		 IntStream.range(0, numberOfSimulations).parallel().forEach(w -> {
			simulations[w] = 
					Math.max(K - currentStock.getValue(sqrtMaturity, generator), 0.0)*discountFactor;
		});
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
