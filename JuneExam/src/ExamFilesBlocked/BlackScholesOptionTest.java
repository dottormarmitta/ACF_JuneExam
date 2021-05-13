package ExamFilesBlocked;

import java.text.DecimalFormat;

import Analytic.BlackScholesAnalytic;
import LinearAlgebra.Statistic;
import Options.PutOptionImplementation;
import Stocks.BlackScholesStock;
import Stocks.StockProcess;

public class BlackScholesOptionTest {

	public static void main(String[] args) throws Exception {

		/*
		 * General parameter
		 */
		double s0  = 1.0;
		double rfr = 0.01;
		double vol = 0.30;
		double K   = 1.03;
		double T   = 1.13;
		int ns     = 60;
		long seed  = 61;

		/*
		 * Stock
		 */
		StockProcess myStock = new BlackScholesStock();
		myStock.setInitialValue(s0);
		myStock.setRiskFreeRate(rfr);
		myStock.setVol(vol);

		/*
		 * Option
		 */
		PutOptionImplementation myPut = new PutOptionImplementation(myStock);
		myPut.setStrike(K);

		/*
		 * Values
		 */
		double analytic  = BlackScholesAnalytic.analyticPut(s0, rfr, vol, K, T);
		double[] values = new double[4];
		long[] nsUsed = new long[4];
		long[] time   = new long[4];
		double[] error = new double[4];
		for (int i = 0; i < 4; i++) {
			nsUsed[i] = ns;
			long startTime = System.nanoTime();
			myPut.simulate(T, Math.exp(-rfr*T), ns, seed);
			long endTime = System.nanoTime();
			time[i] = - startTime + endTime;
			values[i] = myPut.getMonteCarloValue();
			error[i]  = myPut.getMonteCarloError(values[i]);
			ns *= 100;
		}

		/*
		 * Printing
		 */
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(10);
		System.out.println("Exercise 13.5.1, BLACK-SCHOLES MODEL");
		System.out.println();
		System.out.println("Pricing a put option with the following parameters:");
		System.out.println("Stock0" + "\t" + "Strike" + "\t" + "RiskFr" + "\t" + "Volatility");
		System.out.println("1.0000" + "\t" + "1.0300" + "\t" + "0.0100" + "\t" + "0.30");
		System.out.println();
		System.out.println("Benchm value " + "\t" + "MCarlo value" + "\t" + "MCarlo Error  " 
				+ "\t" + "Test Passed?" + "\t" + "Number of sim" + "\t" + "TimeElap (sec)");
		for(int i = 0; i < 4; i++) {
			if(i==3) {
				System.out.println(df.format(analytic) + "\t" + df.format(values[i]) + "\t" + df.format(error[i]) 
				+ "\t" + Statistic.isInside(analytic, values[i]-error[i], values[i]+error[i]) + "\t"+ "\t"
				+ nsUsed[i] + "\t" + + time[i]/1E9);
			} else {
				System.out.println(df.format(analytic) + "\t" + df.format(values[i]) + "\t" + df.format(error[i]) 
				+ "\t" + Statistic.isInside(analytic, values[i]-error[i], values[i]+error[i]) + "\t"+ "\t"
				+ nsUsed[i] + "\t" + "\t" + + time[i]/1E9);
			}
		}
	}

}
