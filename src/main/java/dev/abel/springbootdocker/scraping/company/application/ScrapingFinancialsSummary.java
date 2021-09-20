package dev.abel.springbootdocker.scraping.company.application;

import dev.abel.springbootdocker.scraping.company.domain.EncodedBalanceSheet;
import dev.abel.springbootdocker.scraping.company.domain.EncodedCashFlow;
import dev.abel.springbootdocker.scraping.company.domain.EncodedIncomeStatement;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
class FetchingFinancialSummary {

    public FetchingFinancialSummary() {}


    public EncodedIncomeStatement getIncomeStatementData(Element incomeStatementElement ) {
        EncodedIncomeStatement encodedIncomeStatement = new EncodedIncomeStatement();
        Elements percentageFinancialSummaryValues = incomeStatementElement.select("div.float_lang_base_2").select("div.infoLine");

        String GrossMargin = "";
        String OperatingMargin= "";
        String NetProfitMargin= "";
        String ReturnOnInvestment= "";

        HashMap<String, String> TotalRevenue = new HashMap<>();
        HashMap<String, String> GrossProfit = new HashMap<>();
        HashMap<String, String> OperatingIncome = new HashMap<>();
        HashMap<String, String> NetIncome = new HashMap<>();

        for(int i =0; i< percentageFinancialSummaryValues.size(); i++){
            if(i == 0) {
                GrossMargin = percentageFinancialSummaryValues.get(i)
                        .select("span.float_lang_base_2").text();
            }
            if(i == 1){
                OperatingMargin = percentageFinancialSummaryValues.get(i)
                        .select("span.float_lang_base_2").text();
            }

            if(i == 2){
                NetProfitMargin = percentageFinancialSummaryValues.get(i)
                        .select("span.float_lang_base_2").text();
            }
            if(i == 3){
                ReturnOnInvestment = percentageFinancialSummaryValues.get(i)
                        .select("span.float_lang_base_2").text();
            }
        }
        encodedIncomeStatement.fullPercentage(GrossMargin,OperatingMargin,NetProfitMargin ,ReturnOnInvestment);
        System.out.println(encodedIncomeStatement.toString());
        //THEAD DATES
        Elements datesElements = incomeStatementElement.select("table.genTbl").select("thead").select("th:not(.arial_11 noBold title right period)");
        Elements rows = incomeStatementElement.select("table.genTbl").select("td");
        List<String> dates = new ArrayList<>();
        for(Element dateElement: datesElements){
            dates.add(dateElement.text());
        }
        //TBODY VALUES hashmap ->> total revenue, ...
        for(int i=0; i<rows.size();i++ ){
            if(i == 0) continue;
            switch (i){
                case 1: TotalRevenue = returnTableValue(incomeStatementElement, i, dates);
                    break;
                case 2: GrossProfit = returnTableValue(incomeStatementElement, i, dates);
                    break;
                case 3: OperatingIncome = returnTableValue(incomeStatementElement, i, dates);
                    break;
                case 4: NetIncome = returnTableValue(incomeStatementElement, i, dates);
                    break;
            }
        }

        encodedIncomeStatement.fullValues(TotalRevenue,GrossProfit,OperatingIncome,NetIncome);

        return encodedIncomeStatement;
    }


    public EncodedBalanceSheet getBalancedSheetData(Element balancedSheetElement ) {
        EncodedBalanceSheet encodedBalanceSheet = new EncodedBalanceSheet();
        Elements percentageBalancedSheetElementValues = balancedSheetElement.select("div.float_lang_base_2").select("div.infoLine");

        String QuickRatio = "";
        String CurrentRatio= "";
        String LTDebtToEquity= "";
        String TotalDebtToEquity= "";

        HashMap<String, String> TotalAssets = new HashMap<>();
        HashMap<String, String> TotalLiabilities = new HashMap<>();
        HashMap<String, String> TotalEquity = new HashMap<>();

        for(int i =0; i< percentageBalancedSheetElementValues.size(); i++){
            if(i == 0) {
                QuickRatio = percentageBalancedSheetElementValues.get(i)
                        .select("span.float_lang_base_2").text();
            }
            if(i == 1){
                CurrentRatio = percentageBalancedSheetElementValues.get(i)
                        .select("span.float_lang_base_2").text();
            }

            if(i == 2){
                LTDebtToEquity = percentageBalancedSheetElementValues.get(i)
                        .select("span.float_lang_base_2").text();
            }
            if(i == 3){
                TotalDebtToEquity = percentageBalancedSheetElementValues.get(i)
                        .select("span.float_lang_base_2").text();
            }
        }
        encodedBalanceSheet.fullPercentajes(QuickRatio,CurrentRatio,LTDebtToEquity ,TotalDebtToEquity);
        //THEAD DATES
        Elements datesElements = balancedSheetElement.select("table.genTbl").select("thead").select("th:not(.arial_11 noBold title right period)");
        Elements rows = balancedSheetElement.select("table.genTbl").select("td");
        List<String> dates = new ArrayList<>();
        for(Element dateElement: datesElements){
            dates.add(dateElement.text());
        }
        //TBODY VALUES hashmap ->> total revenue, ...
        for(int i=0; i<rows.size();i++ ){
            if(i == 0) continue;
            switch (i){
                case 1: TotalAssets = returnTableValue(balancedSheetElement, i, dates);
                    break;
                case 2: TotalLiabilities = returnTableValue(balancedSheetElement, i, dates);
                    break;
                case 3: TotalEquity = returnTableValue(balancedSheetElement, i, dates);
                    break;
            }
        }

        encodedBalanceSheet.fullValues(TotalAssets,TotalLiabilities,TotalEquity);
        return encodedBalanceSheet;
    }

    public EncodedCashFlow getCashFlowData(Element incomeStatementElement ) {
        EncodedCashFlow encodedCashFlow = new EncodedCashFlow();
        Elements percentageFinancialSummaryValues = incomeStatementElement.select("div.float_lang_base_2").select("div.infoLine");

        String CashFlowShare = "";
        String RevenueShare= "";
        String OperatingCashFlow= "";

        HashMap<String, String> CashFromOperating = new HashMap<>();
        HashMap<String, String> CashFromInvesting = new HashMap<>();
        HashMap<String, String> CashFromFinancing = new HashMap<>();
        HashMap<String, String> NetChangeInCash = new HashMap<>();

        for(int i =0; i< percentageFinancialSummaryValues.size(); i++){
            if(i == 0) {
                CashFlowShare = percentageFinancialSummaryValues.get(i)
                        .select("span.float_lang_base_2").text();
            }
            if(i == 1){
                RevenueShare = percentageFinancialSummaryValues.get(i)
                        .select("span.float_lang_base_2").text();
            }

            if(i == 2){
                OperatingCashFlow = percentageFinancialSummaryValues.get(i)
                        .select("span.float_lang_base_2").text();
            }

        }
        encodedCashFlow.fullPercentaje(CashFlowShare,RevenueShare,OperatingCashFlow);
        System.out.println(encodedCashFlow.toString());
        //THEAD DATES
        Elements datesElements = incomeStatementElement.select("table.genTbl").select("thead").select("th:not(.arial_11 noBold title right period)");
        Elements rows = incomeStatementElement.select("table.genTbl").select("td");
        List<String> dates = new ArrayList<>();
        for(Element dateElement: datesElements){
            dates.add(dateElement.text());
        }
        //TBODY VALUES hashmap ->> total revenue, ...
        for(int i=0; i<rows.size();i++ ){
            if(i == 0 || i == 1) continue;
            switch (i){
                case 2: CashFromOperating = returnTableValue(incomeStatementElement, i, dates);
                    break;
                case 3: CashFromInvesting = returnTableValue(incomeStatementElement, i, dates);
                    break;
                case 4: CashFromFinancing = returnTableValue(incomeStatementElement, i, dates);
                    break;
                case 5: NetChangeInCash = returnTableValue(incomeStatementElement, i, dates);
                    break;
            }
        }

        encodedCashFlow.fullValues(CashFromOperating,CashFromInvesting,CashFromFinancing,NetChangeInCash);
        return encodedCashFlow;
    }

    public static HashMap<String, String> returnTableValue(Element incomeStatementElement, int i, List<String> dates){
        HashMap<String, String> values = new HashMap<>();
        Elements tableValues = incomeStatementElement.select("table.genTbl").select("tr").get(i).select("td");

        for(int j=0 ; j<dates.size();j++) {
            if(j == 0) continue;
            values.put(dates.get(j) ,tableValues.get(j).text());
        }
        return values;
    }

}
