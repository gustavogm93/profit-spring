package dev.abel.springbootdocker.controller;

import dev.abel.springbootdocker.scraping.ScrapingRegionStrategy;
import dev.abel.springbootdocker.scraping.company.infrastructure.CompanyEncodedDataService;
import dev.abel.springbootdocker.scraping.country.application.ScrapingCountryScrapedData;
import dev.abel.springbootdocker.scraping.country.infrastructure.CountryScrapedDataService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


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

	@GetMapping("/regions")
	public void getRegionExtractedData() throws Exception, IOException {
		scrapingRegion.executor();
	}
	@GetMapping("/normalize/data")
	public void normalize() throws Exception, IOException {
		dataSourceService.normalizeDataSource();
	}

	@GetMapping("/normalize/html")
	public void normalizeHtml() throws Exception, IOException {
		countryScrapedDataService.normalizeCountryScrapedData();
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
