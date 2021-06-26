package VAR;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import Analytic.BlackScholesAnalytic;
import RandomEnv.MersenneRandomGenerator;
import RandomEnv.RandomGenerator;
import Stocks.StockProcess;

public class VarOption implements VARAnalysis {

	private final StockProcess stock;
	private double[] profitLosses;
	private final double middleTime;
	private final double dt;
	private final double strike;
	private int ns;

	public VarOption(StockProcess model, double buyingTime, double optionMaturity, 
			double strike, int ns) {
		this.stock = model;
		this.middleTime = buyingTime;
		this.strike = strike;
		this.dt = optionMaturity - buyingTime;
		this.ns = ns;
	}

	@Override
	public void printHistogram() throws IOException {
		
		BufferedWriter outputWriter = null;
		File file = new File("/Users/guglielmo/Desktop/VarArray");
		outputWriter = new BufferedWriter(new FileWriter(file));
		for(int i = 0; i < profitLosses.length; i++) {
			if(i<profitLosses.length-1) {
				outputWriter.write(Double.toString(profitLosses[i]));
				outputWriter.newLine();
			} else {
				outputWriter.write(Double.toString(profitLosses[i]));
			}
		}
		outputWriter.flush();  
		outputWriter.close();
		
	}

	@Override
	public double getVarFromPercent(double percentage) {
		return profitLosses[(int) ((1.0-percentage)*ns)];
	}

	@Override
	public void simulate(double benchmark, double rfr, double BSvol, int seed) {
		
		profitLosses = new double[ns];
		RandomGenerator generator1 = new MersenneRandomGenerator(seed);
		
		for (int w = 0; w < ns; w++) {
			
			// We calculate s_tm and the market value
			double stoppedStock = stock.getValue(Math.sqrt(middleTime), generator1);
			double analyticValue = BlackScholesAnalytic.analyticPut(stoppedStock, rfr, BSvol, strike, dt);
			
			// Discounting
			analyticValue   *= Math.exp(-rfr*middleTime);
			
			// ProfitLosses:
			profitLosses[w] = benchmark - analyticValue;
		}
		
		// Sort for calculating quantiles
		Arrays.sort(profitLosses);
	}

}
