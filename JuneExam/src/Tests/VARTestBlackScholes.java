package Tests;

import Options.PutOption;
import Options.PutOptionImplementation;
import Stocks.BlackScholesStock;
import Stocks.StockProcess;
import VAR.VARAnalysis;
import VAR.VarOption;

public class VARTestBlackScholes {
	
	public static void main(String[] args) throws Exception {
		
		double s0 = 1.00;
		double T = 1.13;
		double K = 1.03;
		double rfr = 0.01;
		double tm = 0.5;
		double BSvol = 0.24;
		double vol = 0.30;
		int ns = 5000000;
		
		StockProcess bsStock = new BlackScholesStock();
		bsStock.setInitialValue(s0);
		bsStock.setRiskFreeRate(rfr);
		bsStock.setVol(vol);
		
		PutOption put = new PutOptionImplementation(bsStock);
		put.setStrike(K);
		put.simulate(T, Math.exp(-rfr*T), ns, 78382, "mt");
		double simulatedValue = put.getMonteCarloValue();
		
		VARAnalysis VaR = new VarOption(bsStock, tm, T, K, ns);
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

