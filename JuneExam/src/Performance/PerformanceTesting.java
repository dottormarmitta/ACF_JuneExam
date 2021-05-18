package Performance;

import java.text.DecimalFormat;

import Analytic.BlackScholesAnalytic;
import LinearAlgebra.Statistic;
import Options.PutOption;
import Options.PutOptionImplementation;
import Options.PutOptionParallel;
import Stocks.BlackScholesStock;
import Stocks.StockProcess;

public class PerformanceTesting {

	public static void main(String[] args) throws Exception {

		/*
		 * General parameter
		 */
		double s0  = 1.0;
		double rfr = 0.01;
		double vol = 0.30;
		double K   = 1.03;
		double T   = 1.13;
		int ns     = 250000000;
		long seed  = 601;

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
		PutOption myPut = new PutOptionImplementation(myStock);
		myPut.setStrike(K);

		/*
		 * Values
		 */
		double analytic  = BlackScholesAnalytic.analyticPut(s0, rfr, vol, K, T);
		double value = 0.0;
		long time   = 0;
		double error = 0.0;
		long startTime = System.nanoTime();
		myPut.simulate(T, Math.exp(-rfr*T), ns, seed, "lcg");
		long endTime = System.nanoTime();
		time = - startTime + endTime;
		value = myPut.getMonteCarloValue();
		error  = myPut.getMonteCarloError(value);

		/*
		 * Printing
		 */
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(10);
		df.setMinimumFractionDigits(10);
		System.out.println("Pricing a put option with the following parameters: (LCG)");
		System.out.println("Stock0" + "\t" + "Strike" + "\t" + "RiskFr" + "\t" + "Volatility");
		System.out.println("1.0000" + "\t" + "1.0300" + "\t" + "0.0100" + "\t" + "0.30");
		System.out.println();
		System.out.println("Benchm value " + "\t" + "MCarlo value" + "\t" + "MCarlo Error  " 
				+ "\t" + "Test Passed?" + "\t" + "Number of sim" + "\t" + "TimeElap (sec)");
		System.out.println(df.format(analytic) + "\t" + df.format(value) + "\t" + df.format(error) 
		+ "\t" + Statistic.isInside(analytic, value-error, value+error) + "\t"+ "\t"
		+ ns + "\t" + + time/1E9);

		myPut = new PutOptionParallel(myStock);
		myPut.setStrike(K);
		startTime = System.nanoTime();
		myPut.simulate(T, Math.exp(-rfr*T), ns, seed, "mt");
		endTime = System.nanoTime();
		time= - startTime + endTime;
		value = myPut.getMonteCarloValue();
		error  = myPut.getMonteCarloError(value);


		/*
		 * Printing
		 */
		System.out.println();
		System.out.println("-".repeat(95));
		System.out.println();
		System.out.println("Pricing a put option with the following parameters: (MT)");
		System.out.println("Stock0" + "\t" + "Strike" + "\t" + "RiskFr" + "\t" + "Volatility");
		System.out.println("1.0000" + "\t" + "1.0300" + "\t" + "0.0100" + "\t" + "0.30");
		System.out.println();
		System.out.println("Benchm value " + "\t" + "MCarlo value" + "\t" + "MCarlo Error  " 
				+ "\t" + "Test Passed?" + "\t" + "Number of sim" + "\t" + "TimeElap (sec)");
		System.out.println(df.format(analytic) + "\t" + df.format(value) + "\t" + df.format(error) 
		+ "\t" + Statistic.isInside(analytic, value-error, value+error) + "\t"+ "\t"
		+ ns + "\t" + + time/1E9);
	}
}