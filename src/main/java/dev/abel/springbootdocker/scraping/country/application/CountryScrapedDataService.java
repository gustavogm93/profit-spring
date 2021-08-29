package dev.abel.springbootdocker.scraping.country.application;

import dev.abel.springbootdocker.scraping.country.domain.CountryScrapedData;

import java.util.List;

public interface CountryScrapedDataService {

    public List<CountryScrapedData> getAll();

    public List<CountryScrapedData> getByRegion(String regionTitle);

    public void add(CountryScrapedData CountryScrapedData);

    public List<CountryScrapedData> findByTitle(String title);

    public void normalize();

    public List<CountryScrapedData> getHtmlUnncompleted();
}
