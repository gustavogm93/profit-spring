package dev.abel.springbootdocker.controller;

import dev.abel.springbootdocker.scraping.ScrapingRegionStrategy;
import dev.abel.springbootdocker.scraping.company.infrastructure.CompanyEncodedDataService;
import dev.abel.springbootdocker.scraping.country.application.ScrapingHtml;
import dev.abel.springbootdocker.scraping.country.domain.HtmlScraped;
import dev.abel.springbootdocker.scraping.country.infrastructure.DataSourceService;
import dev.abel.springbootdocker.scraping.country.infrastructure.HtmlScrapedService;
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

	@Value("${chrome.driver.path}")
	private String ChromeDriverPathResource;
	private DataSourceService dataSourceService;
	private ScrapingRegionStrategy scrapingRegion;
	private HtmlScrapedService htmlScrapedService;
	private CompanyEncodedDataService companyEncodedDataService;

	private ScrapingHtml scrapingHtml;
	/*private ScrapingCountryStrategy scrapingCountry;
	
	 private ScrapingCoverageStrategy scrapingCoverageCountry;
	
	@Autowired
	public ScrapingController(ScrapingCountryStrategy scrapingCountry, ScrapingRegionStrategy scrapingRegion, ScrapingCoverageStrategy scrapingCoverageCountry ) {
		this.scrapingCountry = scrapingCountry;
		this.scrapingRegion = scrapingRegion;
		this.scrapingCoverageCountry = scrapingCoverageCountry;
	}*/

	public ScrapingController(DataSourceService dataSourceService, ScrapingRegionStrategy scrapingRegion, HtmlScrapedService htmlScrapedService, CompanyEncodedDataService companyEncodedDataService, ScrapingHtml scrapingHtml) {
		this.dataSourceService = dataSourceService;
		this.scrapingRegion = scrapingRegion;
		this.htmlScrapedService = htmlScrapedService;
		this.companyEncodedDataService = companyEncodedDataService;
		this.scrapingHtml = scrapingHtml;
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
		htmlScrapedService.normalizeHtmlScraped();
	}

	@GetMapping("/normalize/null")
	public void normalizeNull() throws Exception, IOException {
		List<HtmlScraped> un = htmlScrapedService.getHtmlUnncompleted();
		System.out.println(un.size());
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
