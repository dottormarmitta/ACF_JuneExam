package Tests;

import Options.PutOption;
import Options.PutOptionImplementation;
import Stocks.HestonStock;
import Stocks.StockProcess;
import VAR.VARAnalysis;
import VAR.VarOption;

public class VARHeston {

	public static void main(String[] args) throws Exception {
		
		/*
		 * General parameter
		 */
		double s0  = 1.00;
		double rfr = 0.01;
		double vol = 0.0635; //eta
		double rho = 0.2125;
		double k   = 0.1153;
		double theta = 0.0240;
		double eta = 0.010;
		double K   = 1.03;
		double T   = 1.130;
		int ns     = 1000000;
		int vt = 200;
		double BSvol = 0.24;
		double tm = 0.5;

		/*
		 * Stock
		 */
		StockProcess hsStock = new HestonStock(rho, k, theta, eta, vt);
		hsStock.setInitialValue(s0);
		hsStock.setRiskFreeRate(rfr);
		hsStock.setVol(vol);

		
		PutOption put = new PutOptionImplementation(hsStock);
		put.setStrike(K);
		put.simulate(T, Math.exp(-rfr*T), ns, 78382, "mt");
		double simulatedValue = put.getMonteCarloValue();
		
		VARAnalysis VaR = new VarOption(hsStock, tm, T, K, ns);
		VaR.simulate(simulatedValue, rfr, BSvol, ns);
		VaR.printHistogram();
		System.out.println("VAR 99% = " + VaR.getVarFromPercent(0.99));
		System.out.println("VAR 95% = " + VaR.getVarFromPercent(0.95));
		System.out.println("VAR 90% = " + VaR.getVarFromPercent(0.90));
		System.out.println("VAR 10% = " + VaR.getVarFromPercent(0.10));
		System.out.println("VAR 05% = " + VaR.getVarFromPercent(0.05));
		System.out.println("VAR 01% = " + VaR.getVarFromPercent(0.01));
		
	}

}
