package ExamFilesUser;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

import RandomEnv.ParallelRandomGenerator;
import RandomEnv.RandomGenerator;
import Stocks.BlackScholesStock;
import Stocks.StockProcess;

public class BlackScholesDistribution {

	public static void main(String[] args) {
		
		System.out.println("Hello!!!! I am bot-pricer \uD83E\uDD16.");
		System.out.println("This program is intended to test my ability to reproduce BS model!");
		System.out.println();
		/*
		 * RAW USER INTERFACE
		 */
		double s0  = 1.0;
		double rfr = 0.01;
		double vol = 0.30;
		double T   = 1.13;
		int ns     = 0;
		
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
		System.out.print("What is the option MATURITY (T)?      ");
		T = s.nextDouble();
		System.out.println();
		System.out.print("What is the rnumber of simulations?   ");
		ns = s.nextInt();
		System.out.println();
		s.close();

		/*
		 * Stock
		 */
		StockProcess myStock = new BlackScholesStock();
		myStock.setInitialValue(s0);
		myStock.setRiskFreeRate(rfr);
		myStock.setVol(vol);
		
		/*
		 * Random Generator
		 */
		RandomGenerator generator = new ParallelRandomGenerator();
		
		/*
		 * Storing array
		 */
		double[] paths = new double[ns];
		double sqrtMaturity = Math.sqrt(T);
		
		IntStream.range(0, ns).parallel().forEach(w -> {
			paths[w] = myStock.getValue(sqrtMaturity, generator);
		});
		
		Arrays.sort(paths);
		
		System.out.println("The stock denisty function has this main features: ");
		System.out.println("P[S_T > " + paths[(int) ((1.0-0.99)*ns)] + " ]  =  0.99");
		System.out.println("P[S_T > " + paths[(int) ((1.0-0.95)*ns)] + " ]  =  0.95");
		System.out.println("P[S_T > " + paths[(int) ((1.0-0.90)*ns)] + " ]  =  0.90");
		System.out.println("P[S_T > " + paths[(int) ((1.0-0.01)*ns)] + " ]  =  0.01");

	}

}
