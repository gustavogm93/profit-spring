package dev.abel.springbootdocker.enums.financialsummary;

import java.util.Arrays;

public enum FinancialSummary {

	TotalRevenue("Total revenue"),
	GrossProfit("Gross Profit"),
	OperatingIncome("Operating Income"),
	NetIncome("Net Income"),
	
	TotalAssets("Total Assets"),
	TotalLiabilities("Total Liabilities"),
	TotalEquity("Total Equity"),

	CashFromOperatingActivities("Cash From Operating Activities"),
	CashFromInvestingActivities("Cash From Investing Activities"),
	CashFromFinancingActivities("Cash From Financing Activities"),
	NetChangeInCash("Net Change in Cash");

	public String title;
	
	FinancialSummary(String title) {
		this.title = title;
	}
	FinancialSummary() {}
	
	public String getTitle() {
		return title;
	}

	public static FinancialSummary getFinancialSummaryByString(String obj) {
		FinancialSummary response = Arrays.asList(FinancialSummary.values()).stream()
				.filter(item -> item.getTitle().equalsIgnoreCase(obj)).findFirst().get();
		return response;
	}


}
