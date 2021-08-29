package dev.abel.springbootdocker.controller;

import dev.abel.springbootdocker.scraping.ScrapingRegionStrategy;
import dev.abel.springbootdocker.scraping.company.application.CompanyEncodedDataService;
import dev.abel.springbootdocker.scraping.country.application.ScrapingCountryScrapedData;
import dev.abel.springbootdocker.scraping.country.application.CountryScrapedDataService;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/scraping")
@Data
@Component
public class ScrapingController {

	private ScrapingRegionStrategy scrapingRegion;
	private CountryScrapedDataService countryScrapedDataService;
	private CompanyEncodedDataService companyEncodedDataService;
	private ScrapingCountryScrapedData scrapingCountryScrapedData;

	public ScrapingController(ScrapingRegionStrategy scrapingRegion, CountryScrapedDataService countryScrapedDataService,
							  CompanyEncodedDataService companyEncodedDataService, ScrapingCountryScrapedData scrapingCountryScrapedData) {
		this.scrapingRegion = scrapingRegion;
		this.countryScrapedDataService = countryScrapedDataService;
		this.companyEncodedDataService = companyEncodedDataService;
		this.scrapingCountryScrapedData = scrapingCountryScrapedData;
	}




	@GetMapping("/html")
	public void getCoverageCountryExtractedData(@RequestParam( "region") String region) throws Exception {
		scrapingHtml.executor(region);
	}

	@GetMapping("/normalize/company")
	public void getNormali(@RequestParam( "region") String region) throws Exception {
		companyEncodedDataService.normalizeCompanyEncodedDataByRegion(region);
	}

	/*@GetMapping("coverage/country")
	public void getCoverageCountryExtractedData(@RequestParam( "region") String region) {
		scrapingCoverageCountry.executor(region);
	}*/
/*
	@GetMapping("/countries")
	public void getCountryExtractedData(@RequestParam( "region") String region) {
		scrapingCountry.executor(region);
	}*/

	/*@GetMapping("coverage/country")
	public void getCoverageCountryExtractedData(@RequestParam( "region") String region) {
		scrapingCoverageCountry.executor(region);
	}*/

	
	
	
	
	
	
	
	
	
	
	
	
}
