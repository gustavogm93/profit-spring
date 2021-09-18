package dev.abel.springbootdocker.scraping.company.application;

import dev.abel.springbootdocker.collections.region.RegionService;
import dev.abel.springbootdocker.scraping.company.domain.*;
import dev.abel.springbootdocker.scraping.country.domain.EncodedData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ScrapingCompanyStrategy {

    RegionService regionService;
    CompanyEncodedDataService companyEncodedDataService;
    FetchingFinancialSummary fetchingFinancialsSummary;
    public ScrapingCompanyStrategy(RegionService regionService, CompanyEncodedDataService companyEncodedDataService,FetchingFinancialSummary fetchingFinancialsSummary) {
        this.regionService = regionService;
        this.companyEncodedDataService = companyEncodedDataService;
        this.fetchingFinancialsSummary = fetchingFinancialsSummary;
    }

    public void run(String regionTitle) {

        List<CompanyScrapedData> companyEncodedList = companyEncodedDataService.getUncommpletedByRegion(regionTitle);

        for(CompanyScrapedData companyScrapedDataBase: companyEncodedList){
            Elements elements = getElements(companyScrapedDataBase);
            if(elements == null || elements.size() < 3){
                continue;
            }
            EncodedIncomeStatement encodedIncomeStatement = fetchingFinancialsSummary.getIncomeStatementData(elements.get(0));
            EncodedBalanceSheet encodedBalanceData = fetchingFinancialsSummary.getBalancedSheetData(elements.get(1));
            EncodedCashFlow encodedCashFlow =  fetchingFinancialsSummary.getCashFlowData(elements.get(2));

        }
    }

public Elements getElements(CompanyScrapedData companyScrapedDataBase){
    String urlSummary = companyScrapedDataBase.getUrls().getFinancialSummary();
    Document doc;
    try {
        doc = Jsoup.connect(urlSummary).get();

        Elements elements = doc.select("div.companySummaryIncomeStatement");
        if (elements == null) {
            companyScrapedDataBase.fullIfEmptyHtml();
            return null;
        }

        return elements;
        } catch (Exception e) {
        companyScrapedDataBase.fullIfEmptyHtml();
        return null;
    }

    }


}