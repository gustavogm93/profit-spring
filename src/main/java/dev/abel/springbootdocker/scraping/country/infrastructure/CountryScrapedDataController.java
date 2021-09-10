package dev.abel.springbootdocker.scraping.country.infrastructure;

import dev.abel.springbootdocker.scraping.country.application.ScrapingInitialCountries;
import dev.abel.springbootdocker.scraping.country.application.CountryScrapedDataService;
import dev.abel.springbootdocker.scraping.country.application.ScrapingCountryData;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/scraping/country")
@Data
@Component
public class CountryScrapedDataController {

    private ScrapingInitialCountries scrapingInitialCountries;
    private CountryScrapedDataService countryScrapedDataService;
    private ScrapingCountryData scrapingCountryData;

    public CountryScrapedDataController(ScrapingInitialCountries scrapingInitialCountries,
                                        CountryScrapedDataService countryScrapedDataService,
                                        ScrapingCountryData scrapingCountryData) {
        this.scrapingInitialCountries = scrapingInitialCountries;
        this.countryScrapedDataService = countryScrapedDataService;
        this.scrapingCountryData = scrapingCountryData;
    }

    @GetMapping("/initials")
    public void getAllInitialCountries() throws Exception, IOException {
        scrapingInitialCountries.executor();
    }

    @GetMapping("/normalize")
    public void normalize() throws Exception, IOException {
        countryScrapedDataService.normalize();
    }

    @GetMapping("/fetch")
    public void fetchCountryDataByRegion(@RequestParam( "region") String region) throws Exception {
        scrapingCountryData.executor(region);
    }


}
