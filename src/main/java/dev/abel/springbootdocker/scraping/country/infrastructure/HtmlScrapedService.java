package dev.abel.springbootdocker.scraping.country.infrastructure;

import dev.abel.springbootdocker.scraping.country.domain.HtmlScraped;

import java.util.List;

public interface HtmlScrapedService {

    public List<HtmlScraped> getAll();

    public List<HtmlScraped> getByRegion(String regionTitle);

    public void add(HtmlScraped HtmlScraped);

    public List<HtmlScraped> findByTitle(String title);

    public void normalizeHtmlScraped();

    public List<HtmlScraped> getHtmlUnncompleted();
}
