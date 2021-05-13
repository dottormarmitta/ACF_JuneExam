package VarianceGammaTest;

import java.text.DecimalFormat;

import Analytic.BlackScholesAnalytic;
import LinearAlgebra.Statistic;
import Options.PutOptionImplementation;
import Stocks.BlackScholesStock;
import Stocks.StockProcess;
import Stocks.VarianceGammaStock;

public class VarianceGammaOptionTest {

	public static void main(String[] args) throws Exception {
		/*
		 * TEST 1
		 */
		/*
		 * General parameter
		 */
		double s0  = 1.0;
		double rfr = 0.00;
		double vol = 0.1664; //eta
		double nu  = 0.0622;
		double th  = -0.7678;
		double K   = 1.03;
		double T   = 1.0;
		int ns     = 1000;
		long seed  = 61;

		/*
		 * Stock
		 */
		StockProcess myStock = new VarianceGammaStock(th, nu);
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
		double analytic  = 0.1136000001;
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
			ns *= 10;
		}

		/*
		 * Printing
		 */
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(10);
		System.out.println("VARIANCE GAMMA MODEL");
		System.out.println();
		System.out.println("Pricing a put option with the following parameters:");
		System.out.println("Stock0" + "\t" + "Strike" + "\t" + "RiskFr" + "\t" + "Vol" + 
				"\t" + "Maturity");
		System.out.println("1.000" + "\t" + "1.030" + "\t" + "0.000" + "\t" + "0.300" + "\t" + "1.0");
		System.out.println();
		System.out.println("Benchm value " + "\t" + "MCarlo value" + "\t" + "MCarlo Error  " 
				+ "\t" + "Test Passed?" + "\t" + "Number of sim" + "\t" + "TimeElap (sec)");
		for(int i = 0; i < 4; i++) {
			System.out.println(df.format(analytic) + "\t" + df.format(values[i]) + "\t" + df.format(error[i]) 
			+ "\t" + Statistic.isInside(analytic, values[i]-error[i], values[i]+error[i]) + "\t"+ "\t"
			+ nsUsed[i] + "\t" + "\t" + + time[i]/1E9);
		}
		
		/*
		 * TEST 2
		 */
		/*
		 * General parameter
		 */
		rfr = 0.05;
		T   = 0.750;
		ns  = 1000;
		
		/*
		 * Stock
		 */
		myStock.setRiskFreeRate(rfr);

		/*
		 * Values
		 */
		analytic  = 0.0803000001;
		for (int i = 0; i < 4; i++) {
			nsUsed[i] = ns;
			long startTime = System.nanoTime();
			myPut.simulate(T, Math.exp(-rfr*T), ns, seed);
			long endTime = System.nanoTime();
			time[i] = - startTime + endTime;
			values[i] = myPut.getMonteCarloValue();
			error[i]  = myPut.getMonteCarloError(values[i]);
			ns *= 10;
		}

		/*
		 * Printing
		 */
		System.out.println();
		System.out.println();
		System.out.println("-".repeat(95));
		System.out.println();
		System.out.println();
		System.out.println("Pricing a put option with the following parameters:");
		System.out.println("Stock0" + "\t" + "Strike" + "\t" + "RiskFr" + "\t" + "Vol" + 
				"\t" + "Maturity");
		System.out.println("1.000" + "\t" + "1.030" + "\t" + "0.050" + "\t" + "0.300" + "\t" + "0.750");
		System.out.println();
		System.out.println("Benchm value " + "\t" + "MCarlo value" + "\t" + "MCarlo Error  " 
				+ "\t" + "Test Passed?" + "\t" + "Number of sim" + "\t" + "TimeElap (sec)");
		for(int i = 0; i < 4; i++) {
			System.out.println(df.format(analytic) + "\t" + df.format(values[i]) + "\t" + df.format(error[i]) 
			+ "\t" + Statistic.isInside(analytic, values[i]-error[i], values[i]+error[i]) + "\t"+ "\t"
			+ nsUsed[i] + "\t" + "\t" + + time[i]/1E9);
		}
	}

}
