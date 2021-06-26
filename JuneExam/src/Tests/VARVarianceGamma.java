package Tests;

import Options.PutOption;
import Options.PutOptionImplementation;
import Stocks.StockProcess;
import Stocks.VarianceGammaStock;
import VAR.VARAnalysis;
import VAR.VarOption;

public class VARVarianceGamma {
	public static void main(String[] args) throws Exception {
		
		double s0  = 1.00;
		double rfr = 0.01;
		double vol = 0.1664; //eta
		double nu  = 0.0622;
		double th  = -0.7678;
		double K   = 1.03;
		double T   = 1.13;
		double tm = 0.5;
		double BSvol = 0.24;
		int ns = 500000;
		
		StockProcess vgStock = new VarianceGammaStock(th, nu);
		vgStock.setInitialValue(s0);
		vgStock.setRiskFreeRate(rfr);
		vgStock.setVol(vol);
		
		PutOption put = new PutOptionImplementation(vgStock);
		put.setStrike(K);
		put.simulate(T, Math.exp(-rfr*T), ns, 78382, "mt");
		double simulatedValue = put.getMonteCarloValue();
		
		VARAnalysis VaR = new VarOption(vgStock, tm, T, K, ns);
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

