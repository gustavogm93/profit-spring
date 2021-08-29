package dev.abel.springbootdocker.scraping.company.application;

import dev.abel.springbootdocker.scraping.company.domain.CompanyEncodedData;


import java.util.List;

public interface CompanyEncodedDataService {

    public List<CompanyEncodedData> getAll();

    public void add(CompanyEncodedData HtmlScraped);

    public List<CompanyEncodedData> findByTitle(String title);

    public List<CompanyEncodedData> findByCode(String title);

    public void normalize(String region) throws Exception;

    public List<CompanyEncodedData> getHtmlUnncompleted();


}
