package ExamFilesUser;

import java.util.Arrays;
import java.util.Scanner;
import RandomEnv.MersenneRandomGenerator;
import RandomEnv.RandomGenerator;
import Stocks.StockProcess;
import Stocks.VarianceGammaStock;

public class VarianceGammaDistribution {

	public static void main(String[] args) {
		
		System.out.println("Hello!!!! I am bot-pricer \uD83E\uDD16.");
		System.out.println("This program is intended to test my ability to reproduce BS model!");
		System.out.println();
		
		/*
		 * RAW USER INTERFACE
		 */
		double s0;
		double rfr;
		double vol; //nu_0
		double theta;
		double nu;
		double T;
		int ns     = 50;
		long seed  = 4903;
		Scanner s = new Scanner(System.in);
		
		System.out.print("What is the STOCK value today (S_0)?   ");
		s0 = s.nextDouble();
		System.out.println();
		
		System.out.print("What is the RISK-FREE rate (r)?        ");
		rfr = s.nextDouble();
		System.out.println();
		
		System.out.print("What is the the volatility (ETA)?      ");
		vol = s.nextDouble();
		System.out.println();
		
		System.out.print("What is the value of the param theta?  ");
		theta = s.nextDouble();
		System.out.println();
		
		System.out.print("What is the value of the param nu?     ");
		nu = s.nextDouble();
		System.out.println();
		
		System.out.print("What is the option MATURITY (T)?       ");
		T = s.nextDouble();
		System.out.println();
		
		System.out.print("What is the random generator SEED?     ");
		seed = s.nextLong();
		System.out.println();
		
		System.out.print("What is the number of simulations?     ");
		ns = s.nextInt();
		System.out.println();
		
		s.close();
		
		/*
		 * Stock
		 */
		StockProcess myStock = new VarianceGammaStock(theta, nu);
		myStock.setInitialValue(s0);
		myStock.setRiskFreeRate(rfr);
		myStock.setVol(vol);

		/*
		 * Random Generator
		 */
		RandomGenerator generator = new MersenneRandomGenerator(seed);
		
		/*
		 * Storing array
		 */
		double[] paths = new double[ns];
		double sqrtMaturity = Math.sqrt(T);
		
		for(int w = 0; w < ns; w++) {
			paths[w] = myStock.getValue(sqrtMaturity, generator);
		}
		
		Arrays.sort(paths);
		
		System.out.println("The stock denisty function has this main features: ");
		System.out.println("P[S_T > " + paths[(int) ((1.0-0.99)*ns)] + " ]  =  0.99");
		System.out.println("P[S_T > " + paths[(int) ((1.0-0.95)*ns)] + " ]  =  0.95");
		System.out.println("P[S_T > " + paths[(int) ((1.0-0.90)*ns)] + " ]  =  0.90");
		System.out.println("P[S_T > " + paths[(int) ((1.0-0.01)*ns)] + " ]  =  0.01");

	}

}
