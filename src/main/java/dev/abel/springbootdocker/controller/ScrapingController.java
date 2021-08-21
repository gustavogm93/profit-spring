package dev.abel.springbootdocker.controller;

import dev.abel.springbootdocker.scraping.ScrapingCountryStrategy;
import dev.abel.springbootdocker.scraping.ScrapingCoverageStrategy;
import dev.abel.springbootdocker.scraping.ScrapingRegionStrategy;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/scraping")
@Data
@Component
public class ScrapingController {

	@Value("${chrome.driver.path}")
	private String ChromeDriverPathResource;

	private ScrapingRegionStrategy scrapingRegion;

	/*private ScrapingCountryStrategy scrapingCountry;
	
	 private ScrapingCoverageStrategy scrapingCoverageCountry;
	
	@Autowired
	public ScrapingController(ScrapingCountryStrategy scrapingCountry, ScrapingRegionStrategy scrapingRegion, ScrapingCoverageStrategy scrapingCoverageCountry ) {
		this.scrapingCountry = scrapingCountry;
		this.scrapingRegion = scrapingRegion;
		this.scrapingCoverageCountry = scrapingCoverageCountry;
	}*/

	@Autowired
	public ScrapingController(ScrapingRegionStrategy scrapingRegion ) {
		this.scrapingRegion = scrapingRegion;
	}


	@GetMapping("/regions")
	public void getRegionExtractedData() throws Exception, IOException {
		scrapingRegion.executor();
	}
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
