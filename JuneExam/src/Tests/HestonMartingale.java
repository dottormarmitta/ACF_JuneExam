package Tests;

import java.text.DecimalFormat;

import LinearAlgebra.Statistic;
import RandomEnv.MersenneRandomGenerator;
import RandomEnv.RandomGenerator;
import Stocks.HestonStock;
import Stocks.StockProcess;

public class HestonMartingale {

	public static void main(String[] args) throws Exception {
		
		double s0  = 103.72;
		double rfr = 0.015;
		double vol = 0.0635; //nu_0
		double rho = 0.2125;
		double k   = 0.1153;
		double theta = 0.0240;
		double eta = 0.010;
		double[] T   = {0.25, 0.50, 0.75, 1.00, 1.25, 1.50, 1.75, 2.0};
		int ns     = 1000000;
		long seed  = 601;

		/*
		 * Stock
		 */
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(10);
		df.setMinimumFractionDigits(10);
		StockProcess myStock = new HestonStock(rho, k, theta, eta, 100);
		myStock.setInitialValue(s0);
		myStock.setRiskFreeRate(rfr);
		myStock.setVol(vol);
		RandomGenerator generator = new MersenneRandomGenerator(seed);
		for(int t = 0; t < T.length; t++) {
			double[] stockValue = new double[ns];
			for (int i = 0; i < ns; i++) {
				stockValue[i] = myStock.getValue(Math.sqrt(T[t]), generator);
			}
			System.out.println();
			double mean = Statistic.getAverage(stockValue);
			double mcErr = 3*Math.sqrt(Statistic.getVariance(stockValue, mean))/Math.sqrt(ns);
			mean *= Math.exp(-rfr*T[t]);
			System.out.println("Maturity" + "\t" + "Theoretical Value " + "\t" + "MCarlo value" + "\t" + "MCarlo Error  " 
					+ "\t" + "Test Passed?" + "\t" + "Number of sim");
			System.out.println(df.format(T[t]) + "\t" + df.format(s0) + "\t" + "\t" + df.format(mean) + "\t" + df.format(mcErr) 
			+ "\t" + Statistic.isInside(s0, mean-mcErr, mean+mcErr) + "\t" + "\t" + ns);
		}
	}



}
