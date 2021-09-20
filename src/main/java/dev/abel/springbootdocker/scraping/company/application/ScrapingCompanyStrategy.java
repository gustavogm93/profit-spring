package dev.abel.springbootdocker.scraping.company.application;

import dev.abel.springbootdocker.collections.region.RegionService;
import dev.abel.springbootdocker.scraping.company.domain.*;
import dev.abel.springbootdocker.scraping.country.application.ScrapingCountryData;
import dev.abel.springbootdocker.scraping.country.domain.EncodedData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScrapingCompanyStrategy {

    RegionService regionService;
    CompanyEncodedDataService companyEncodedDataService;
    FetchingFinancialSummary fetchingFinancialsSummary;
    FetchingProfileCompany fetchingProfileCompany;

    public ScrapingCompanyStrategy(RegionService regionService, CompanyEncodedDataService companyEncodedDataService,
                                   FetchingFinancialSummary fetchingFinancialsSummary, FetchingProfileCompany fetchingProfileCompany) {
        this.regionService = regionService;
        this.companyEncodedDataService = companyEncodedDataService;
        this.fetchingFinancialsSummary = fetchingFinancialsSummary;
        this.fetchingProfileCompany = fetchingProfileCompany;
    }

    private static final Logger logger = LoggerFactory.getLogger(ScrapingCompanyStrategy.class);

    public void run(String regionTitle) {
        logger.info("start scraping company process");
        List<CompanyScrapedData> companyEncodedList = companyEncodedDataService.getUncommpletedByRegion(regionTitle);

        for(CompanyScrapedData companyScrapedDataBase: companyEncodedList){
            logger.info("scraping {}..." ,companyScrapedDataBase.getTitle());

            Elements elements = getElements(companyScrapedDataBase);

            EncodedIncomeStatement encodedIncomeStatement = elements != null ? fetchingFinancialsSummary.getIncomeStatementData(elements.get(0)) : null;
            EncodedBalanceSheet encodedBalanceData = elements != null ? fetchingFinancialsSummary.getBalancedSheetData(elements.get(1)) : null;
            EncodedCashFlow encodedCashFlow =  elements != null ? fetchingFinancialsSummary.getCashFlowData(elements.get(2)) : null;

            EncodedProfile encodedProfile = fetchingProfileCompany.getCompanyProfile(companyScrapedDataBase.getUrls().getProfile());

            EncodedCompanyData encodedCompanyData = new EncodedCompanyData(encodedProfile, encodedIncomeStatement,encodedCashFlow,encodedBalanceData);
            companyScrapedDataBase.fillCompanyEncodedData(encodedCompanyData);


            logger.info("the company: {} was completed scraped", companyScrapedDataBase.getTitle());
        }
    }

public Elements getElements(CompanyScrapedData companyScrapedDataBase){
    String urlSummary = companyScrapedDataBase.getUrls().getFinancialSummary();
    Document doc;
    try {
        doc = Jsoup.connect(urlSummary).get();

        Elements elements = doc.select("div.companySummaryIncomeStatement");
        if (elements == null || elements.size() == 0) {
            return null;
        }

        return elements;
        } catch (Exception e) {
        return null;
    }

    }




}