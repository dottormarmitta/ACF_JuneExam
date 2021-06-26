package Tests;

import java.text.DecimalFormat;

import Analytic.BlackScholesAnalytic;
import LinearAlgebra.Statistic;
import Options.PutOption;
import Options.PutOptionImplementation;
import Stocks.BlackScholesStock;
import Stocks.StockProcess;

public class BSOption {
	
	public static void main(String[] args) throws Exception {
		double s0  = 103.72;
		double rfr = 0.015;
		double vol = 0.33;
		double[] T   = {0.25, 0.50, 0.75, 1.00, 1.25, 1.50, 1.75, 2.0};
		int ns     = 50000000;
		long seed  = 601;
		double K = 105.00;

		/*
		 * Stock
		 */
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(10);
		df.setMinimumFractionDigits(10);
		StockProcess myStock = new BlackScholesStock();
		myStock.setInitialValue(s0);
		myStock.setRiskFreeRate(rfr);
		myStock.setVol(vol);
		PutOption option = new PutOptionImplementation(myStock);
		option.setStrike(K);
		for(int t = 0; t < T.length; t++) {
			System.out.println();
			option.simulate(T[t], Math.exp(-rfr*T[t]), ns, seed, "mt");
			double mean = option.getMonteCarloValue();
			double mcErr = option.getMonteCarloError(mean);
			double analytic = BlackScholesAnalytic.analyticPut(s0, rfr, vol, K, T[t]);
			System.out.println("Maturity" + "\t" + "Theoretical Value " + "\t" + "MCarlo value" + "\t" + "MCarlo Error  " 
					+ "\t" + "Test Passed?" + "\t" + "Number of sim");
			System.out.println(df.format(T[t]) + "\t" + df.format(analytic) + "\t" + "\t" + df.format(mean) + "\t" + df.format(mcErr) 
			+ "\t" + Statistic.isInside(analytic, mean-mcErr, mean+mcErr) + "\t" + "\t" + ns);
		}
	}



}


