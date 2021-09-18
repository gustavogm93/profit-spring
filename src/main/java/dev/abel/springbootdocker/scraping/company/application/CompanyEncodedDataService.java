package dev.abel.springbootdocker.scraping.company.application;

import dev.abel.springbootdocker.scraping.company.domain.CompanyScrapedData;


import java.util.List;

public interface CompanyEncodedDataService {

    public List<CompanyScrapedData> getAll();

    public void add(CompanyScrapedData HtmlScraped);

    public List<CompanyScrapedData> findByTitle(String title);

    public List<CompanyScrapedData> findByCode(String title);

    public void normalize(String region) throws Exception;

    public List<CompanyScrapedData> getUncommpletedByRegion(String regionTitle);


}
