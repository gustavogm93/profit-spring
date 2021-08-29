package dev.abel.springbootdocker.scraping.company.infrastructure;

import dev.abel.springbootdocker.scraping.company.domain.CompanyEncodedData;
import dev.abel.springbootdocker.scraping.country.domain.HtmlScraped;

import java.util.List;

public interface CompanyEncodedDataService {

    public List<CompanyEncodedData> getAll();

    public void add(CompanyEncodedData HtmlScraped);

    public List<CompanyEncodedData> findByTitle(String title);

    public void normalizeCompanyEncodedData();

    public List<CompanyEncodedData> getHtmlUnncompleted();
}
