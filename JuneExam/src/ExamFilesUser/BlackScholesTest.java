package ExamFilesUser;

import java.text.DecimalFormat;
import java.util.Scanner;

import Analytic.BlackScholesAnalytic;
import LinearAlgebra.Statistic;
import Options.CallOptionImplementation;
import Options.Option;
import Options.PutOptionImplementation;
import RandomEnv.BasicRandomGenerator;
import RandomEnv.RandomGenerator;
import Stocks.BlackScholesStock;
import Stocks.StockProcess;

public class BlackScholesTest {

	public static void main(String[] args) throws Exception {
		System.out.println("Hello!!!! I am bot-pricer \uD83E\uDD16. I will help you  pricing  an option.");
		System.out.println("This program is intended to test my ability to reproduce BS model!");
		System.out.println();
		/*
		 * RAW USER INTERFACE
		 */
		double s0  = 1.0;
		double rfr = 0.01;
		double vol = 0.30;
		double K   = 1.03;
		double T   = 1.13;
		int ns     = 60;
		long seed  = 601;
		String typeOpt = "c";
		Scanner s = new Scanner(System.in);
		System.out.print("What is the STOCK value today (S_0)?  ");
		s0 = s.nextDouble();
		System.out.println();
		System.out.print("What is the RISK-FREE rate (r)?       ");
		rfr = s.nextDouble();
		System.out.println();
		System.out.print("What is the stock VOLATILITY (sigma)? ");
		vol = s.nextDouble();
		System.out.println();
		System.out.print("Is the option call (c) or put (p)?    ");
		typeOpt = s.next();
		System.out.println();
		System.out.print("What is the option STRIKE value (K)?  ");
		K = s.nextDouble();
		System.out.println();
		System.out.print("What is the option MATURITY (T)?      ");
		T = s.nextDouble();
		System.out.println();
		System.out.print("What is the random generator SEED?    ");
		seed = s.nextLong();
		System.out.println();

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
		Option myOpt;
		double analytic = 0.0;
		if (typeOpt == "c") {
			myOpt = new CallOptionImplementation(myStock);
			((CallOptionImplementation) myOpt).setStrike(K);
			analytic  = BlackScholesAnalytic.analyticCall(s0, rfr, vol, K, T);
		} else {
			myOpt = new PutOptionImplementation(myStock);
			((PutOptionImplementation) myOpt).setStrike(K);
			analytic  = BlackScholesAnalytic.analyticPut(s0, rfr, vol, K, T);
		}

		/*
		 * Values
		 */
		double[] values = new double[4];
		long[] nsUsed = new long[4];
		long[] time   = new long[4];
		double[] error = new double[4];
		for (int i = 0; i < 4; i++) {
			nsUsed[i] = ns;
			long startTime = System.nanoTime();
			myOpt.simulate(T, Math.exp(-rfr*T), ns, seed);
			long endTime = System.nanoTime();
			time[i] = - startTime + endTime;
			values[i] = myOpt.getMonteCarloValue();
			error[i]  = myOpt.getMonteCarloError(values[i]);
			ns *= 100;
		}

		/*
		 * Printing
		 */
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(10);
		df.setMinimumFractionDigits(10);
		System.out.println("Results: ");
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
		
		/*
		 * MARTINGALE TEST:
		 */
		System.out.println();
		System.out.println("-".repeat(95));
		System.out.println("-".repeat(95));
		System.out.println();
		int test = 0;
		double t = 0.0;
		System.out.println("Please be aware that I am a \uD83E\uDD16 : 1 -> yes, 0 -> no");
		System.out.print("Do you want to proceed with the martingality test (1/0)? ");
		test = s.nextInt();
		System.out.println();
		RandomGenerator generator = new BasicRandomGenerator(seed+8);
		ns = 10000000;
		if (test == 1) {
			System.out.print("Enter the time horizon: ");
			t = s.nextDouble();
			t = Math.sqrt(t);
			System.out.println();
			double[] stockValue = new double[ns];
			for (int i = 0; i < ns; i++) {
				stockValue[i] = myStock.getValue(t, generator);
			}
			System.out.println("Results: ");
			System.out.println();
			double mean = Statistic.getAverage(stockValue);
			double mcErr = 3*Math.sqrt(Statistic.getVariance(stockValue, mean))/Math.sqrt(ns);
			mean *= Math.exp(-rfr*t*t);
			System.out.println("Theoretical Value " + "\t" + "MCarlo value" + "\t" + "MCarlo Error  " 
					+ "\t" + "Test Passed?" + "\t" + "Number of sim");
			System.out.println(df.format(s0) + "\t" + "\t" + df.format(mean) + "\t" + df.format(mcErr) 
					+ "\t" + Statistic.isInside(s0, mean-mcErr, mean+mcErr) + "\t" + "\t" + ns);
		}
		s.close();
		System.out.println("");
		System.out.println("Thank you for bearing with me!");
		System.out.println("Goodbye! \uD83D\uDE42");
		
	}

}

