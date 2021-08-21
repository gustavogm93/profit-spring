package dev.abel.springbootdocker.scraping;

import dev.abel.springbootdocker.scraping.selenium.InvestmentEquityPage;
import org.springframework.stereotype.Component;


@Component
public class ScrapingCoverageStrategy extends InvestmentEquityPage {
/*
	private CoverageRegionService coverageRegionService;

	private RegionService regionService;


	private static final Logger log = LoggerFactory.getLogger(ScrapingCoverageStrategy.class);

	private ScrapingCoverageStrategy(RegionService regionService, CoverageRegionService coverageRegionService) {
		this.regionService = regionService;
		this.coverageRegionService = coverageRegionService;
	}

	public void executor(String regionCode) {

		startSeleniumDriver();
		setUpSeleniumDriver();

		List<CountryProp> countries = getNotCoveredCountries(regionCode);
		
		regions.stream().forEach(region -> {

			region.getCountries().stream().limit(1).forEach(country -> {

				try {
					fetchProcess(country, region.getProperties());
				} catch (Exception e) {
					log.error(Msg.error(country, e.getMessage()));
					driver.close();
					
					startSeleniumDriver();
					setUpSeleniumDriver();
				}

			});
			driver.close();
		});

	}//TODO: COUNTRIES NOT PROP, WOULD BE ID ONLY 

	private List<CountryProp> getNotCoveredCountries(String regionCode) throws Exception {

		CoverageRegion coverageRegion;
		
		log.info("Getting countries in Region: {}", regionCode);
		Optional<RegionDTO> optionalRegion = regionService.findByCode(regionCode).stream().findFirst();
		
		if(optionalRegion.isEmpty()) 
			throw new Exception("Region doesn't exist, pull from investment through scraping region");
		
		Optional<CoverageRegion> optionalCoverageRegion = getCoverageRegion(regionCode).stream().findFirst();
				
		if(optionalCoverageRegion.isEmpty()) 
			throw new Exception("Coverage Region doesn't exist, pull from investment through scraping coverage");
		
		 coverageRegion = optionalCoverageRegion.get();
		 
		if(coverageRegion.getCoverage() == 100) 
			throw new Exception("Region covered");
		
		
		ImmutableList<CountryProp> countries = coverageRegion.getCountries().stream().filter(withoutCoverage)
																			.map(getCountryProp)
																			.collect(ImmutableList.toImmutableList());
		if(countries.isEmpty())
			throw new Exception("countries are covered");
		
		
		return countries;

	}
	
	

	private List<CoverageRegion> getCoverageRegion (String regionTitle){
		List<CoverageRegion> coverageRegionList = coverageRegionService.findByTitle(regionTitle);
		
		return coverageRegionList;
	}
	
	
	
	
	private void fetchProcess(CountryProp country, RegionProp region) throws Exception {
		
		log.info("Starting fetching process of country: {} ", country.getTitle());
		
		goToCountryPage(country.getCode());

		List<CoverageMarketIndex> coverageMarketIndexList = fetchMarketIndexes();

		buildAndSaveCoverageCountry(country, region, coverageMarketIndexList);

	}

	private List<CoverageMarketIndex> fetchMarketIndexes() {
		log.info("Starting fetching market Index");
		
		List<CoverageMarketIndex> coverageMarketIndexList = new ArrayList<>();
		
		for (WebElement optionMarketIndex : getOptionsOfMarketIndex()) {

			var idMarketIndex = getIdMarketIndex(optionMarketIndex);
			var titleMarketIndex = getTitleMarketIndex(optionMarketIndex);
			log.info("Getting market Index of {}", titleMarketIndex);
			
			optionMarketIndex.click();
			wait.until(checkForTableShares());
			
			log.info("Getting shares of market Index {}", titleMarketIndex);
			int shareQuantities = getSharesByElement();

			CoverageMarketIndex coverageMarketIndex = new CoverageMarketIndex(idMarketIndex, titleMarketIndex,
					shareQuantities);

			coverageMarketIndexList.add(coverageMarketIndex);

		}
		return coverageMarketIndexList;
	}

	private int getSharesByElement() {
		int shareQuantities = 0;
		for (WebElement shareElement : getShareElements()) {

			String shareTitle = getShareTitle(shareElement);
			String shareId = getShareId(shareElement);

			if (shareId.length() > 0 && shareTitle.length() > 0)
				shareQuantities++;

		}
		return shareQuantities;
	}

	private int getSharesByCountry(List<CoverageMarketIndex> coverageMarketIndexList) throws Exception {

		Optional<CoverageMarketIndex> optionalCoverageMarketIndexOfGeneralCountry = coverageMarketIndexList.stream()
				.filter(isOverallMarketIndex).findFirst();

		if (optionalCoverageMarketIndexOfGeneralCountry.isPresent()) {
			CoverageMarketIndex coverageMarketIndexOfGeneralCountry = optionalCoverageMarketIndexOfGeneralCountry.get();
			Integer sharesQuantityOfCountry = coverageMarketIndexOfGeneralCountry.getTotalShares();
			return sharesQuantityOfCountry;
		}

		throw new Exception("Error getting general Country market index when get Shares by Country");
	}

	public static Predicate<CoverageMarketIndex> isOverallMarketIndex = (marketIndex) -> {
		return marketIndex.getId().equalsIgnoreCase("all");
	};

	private void buildAndSaveCoverageCountry(CountryProp country, RegionProp region,
			List<CoverageMarketIndex> coverageMarketIndexList) throws Exception {

		Integer sharesQuantityByCountry = getSharesByCountry(coverageMarketIndexList);

		CoverageCountry coverageCountry = new CoverageCountry(country.getCode(), country.getTitle(),
				coverageMarketIndexList, sharesQuantityByCountry, sharesQuantityByCountry);

		coverageCountryService.add(coverageCountry);

	}
*/
}
