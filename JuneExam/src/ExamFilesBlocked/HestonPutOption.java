package ExamFilesBlocked;

import java.text.DecimalFormat;

import LinearAlgebra.Statistic;
import Options.PutOptionImplementation;
import Stocks.StockProcess;
import Stocks.HestonStock;

public class HestonPutOption {

	public static void main(String[] args) throws Exception {
		
		/*
		 * General parameter
		 */
		double s0  = 1.0;
		double rfr = 0.01;
		double vol = 0.0635; //eta
		double rho = 0.2125;
		double k   = 0.1153;
		double theta = 0.0240;
		double eta = 0.010;
		double K   = 1.03;
		double T   = 1.13;
		int ns     = 60;
		long seed  = 4903;

		/*
		 * Stock
		 */
		StockProcess myStock = new HestonStock(rho, k, theta, eta);
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
		double analytic  = 0.1146000001;
		double discrepa  = 7.9E-4;
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
		System.out.println("Exercise 13.5.1, HESTON MODEL");
		System.out.println();
		System.out.println("Pricing a put option with the following parameters:");
		System.out.println("Stock0" + "\t" + "Strike" + "\t" + "RiskFr" + "\t" + "Kappa  " +
				"\t" + "Theta" + "\t" + "Nu_0  " + "\t" + "Correl " + "\t" + "Eta  " +
				"\t" + "Maturity");
		System.out.println("1.0000" + "\t" + "1.0300" + "\t" + "0.0100" + "\t" + "0.1153" +
				"\t" + "0.0240  "  + "0.0635" + "\t" + "0.2125" +
				"\t" + "0.0100 " + "\t" + "1.13");
		System.out.println();
		System.out.println("Benchm value " + "\t" + "MCarlo value" + "\t" + "MCarlo Error  " 
				+ "\t" + "Test Passed?" + "\t" + "Number of sim" + "\t" + "TimeElap (sec)");
		for(int i = 0; i < 4; i++) {
			if(i==3) {
				System.out.println(df.format(analytic) + "\t" + df.format(values[i]) + "\t" + df.format(error[i]) 
				+ "\t" + Statistic.isInside(analytic, values[i]-error[i], values[i]+error[i], discrepa) + "\t"+ "\t"
				+ nsUsed[i] + "\t" + + time[i]/1E9);
			} else {
				System.out.println(df.format(analytic) + "\t" + df.format(values[i]) + "\t" + df.format(error[i]) 
				+ "\t" + Statistic.isInside(analytic, values[i]-error[i], values[i]+error[i], discrepa) + "\t"+ "\t"
				+ nsUsed[i] + "\t" + "\t" + + time[i]/1E9);
			}
		}

	}

}

