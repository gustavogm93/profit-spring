package dev.abel.springbootdocker.scraping;


import dev.abel.springbootdocker.collections.country.CountryDTO;
import dev.abel.springbootdocker.collections.country.CountryProp;
import dev.abel.springbootdocker.collections.country.CountryService;
import dev.abel.springbootdocker.collections.country.CoverageCountry;
import dev.abel.springbootdocker.collections.marketIndex.MarketIndexDTO;
import dev.abel.springbootdocker.collections.marketIndex.MarketIndexProp;
import dev.abel.springbootdocker.collections.marketIndex.MarketIndexService;
import dev.abel.springbootdocker.collections.region.RegionDTO;
import dev.abel.springbootdocker.collections.region.RegionProp;
import dev.abel.springbootdocker.collections.region.RegionService;
import dev.abel.springbootdocker.collections.share.ShareProp;
import dev.abel.springbootdocker.generics.Property;
import dev.abel.springbootdocker.scraping.selenium.InvestmentEquityPage;
import dev.abel.springbootdocker.utils.Msg;
import com.google.common.collect.ImmutableList;
import com.mongodb.lang.Nullable;
import org.apache.poi.ss.formula.functions.Count;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static dev.abel.springbootdocker.collections.region.RegionDTO.byNotCoveraged;

import static dev.abel.springbootdocker.generics.Mapper.MarketIndexDtoToMarketIndexProp;

@Component 
public class ScrapingCountryStrategy extends InvestmentEquityPage  {
/*
	private RegionService regionService;

	private CountryService countryService;

	private MarketIndexService marketIndexService;
	 
	private static final Logger log = LoggerFactory.getLogger(ScrapingCountryStrategy.class);

	private ScrapingCountryStrategy(RegionService regionService, MarketIndexService marketIndexService,
		CountryService countryService) {
		this.regionService = regionService;
		this.marketIndexService = marketIndexService;
		this.countryService = countryService;
	}

	public void executor(@Nullable String regionTitle) throws Exception {

		startSeleniumDriver();
		setUpSeleniumDriver();

		ImmutableList<RegionDTO> regions = getRegionToFetch(regionTitle);
		checkRegionIsCovered(regions);

		log.info("Region: {} is Uncovered, go through scraping for get countries (market index, shares)", regionTitle);
		for (RegionDTO region: regions) {
			ImmutableList<CountryDTO> countries = getCountriesToFetch(region);

			countries.forEach(country -> {
				try {
					fetchProcess(country, region);
				} catch (Exception e) {
					log.error("An error was occurred when scraping {}, error: {}",country, e.getMessage());
					closeSeleniumDriver();
					startSeleniumDriver();
					setUpSeleniumDriver();
				}
			});
		}
		driver.close();
	}
	public ImmutableList<RegionDTO> getRegionToFetch(@Nullable String regionTitle) {
		
		if (Objects.isNull(regionTitle) || regionTitle.isEmpty()) {
			log.info("Getting list of all regions..");

			ImmutableList<RegionDTO> region = regionService.getAll().stream().filter(byNotCoveraged).collect(ImmutableList.toImmutableList());
			
			return region;
		}
			log.info(String.format("Getting %s",regionTitle));
			
			ImmutableList<RegionDTO> region = regionService.findByTitle(regionTitle).stream().filter(byNotCoveraged).collect(ImmutableList.toImmutableList());
			
			return region;
	}

	public ImmutableList<CountryDTO> getCountriesToFetch(RegionDTO region) throws Exception {

			log.info("getting uncovered countries from {} region when get countries to fetch", region.getTitle());
			return countryService.getCountriesUncoveredByRegion(region.getCode());
		}


	private void fetchProcess(CountryDTO country, RegionDTO region) throws Exception {

		goToCountryPage(country.getTitle());
		
		List<MarketIndexDTO> MarketIndexList = fetchMarketIndexes(driver);

		buildAndSaveCountry(country, region, MarketIndexList);
		
		saveMarketIndexList(MarketIndexList);

	}

	
	private void saveMarketIndexList(List<MarketIndexDTO> MarketIndexList) {
		
		marketIndexService.addAll(MarketIndexList);
		
	}

	private List<MarketIndexDTO> fetchMarketIndexes(WebDriver driver) {

		List<MarketIndexDTO> MarketIndexDTOList = new ArrayList<>();

		var countryID = getCountryId();

		for (WebElement optionMarketIndex : getOptionsOfMarketIndex()) {
			log.info(Msg.fetchingMarketIndex);

			var idMarketIndex = getIdMarketIndex(optionMarketIndex);
			var titleMarketIndex = getTitleMarketIndex(optionMarketIndex);

			optionMarketIndex.click();
			wait.until(checkForTableShares());

			TreeSet<ShareProp> shares = getSharesByElement(driver);

			MarketIndexProp marketIndex = new MarketIndexProp(idMarketIndex, titleMarketIndex);
			MarketIndexDTO marketIndexDTO = new MarketIndexDTO(idMarketIndex, countryID, marketIndex, shares);

			MarketIndexDTOList.add(marketIndexDTO);

		}
		return MarketIndexDTOList;
	}

	private TreeSet<ShareProp> getSharesByElement(WebDriver driver) {
		log.info(Msg.fetchingShares);
		TreeSet<ShareProp> shareList = new TreeSet<ShareProp>(Property.byTitle);
		
		for (WebElement shareElement : getShareElements()) {

			String shareTitle = getShareTitle(shareElement);
			String shareId = getShareId(shareElement);

			ShareProp share = new ShareProp(shareId, shareTitle);
			shareList.add(share);

		}
		log.info(Msg.saveShares);
		return shareList;
	}
	//var country has only title
	private CountryProp getCountryToSave(CountryProp country, WebDriver driver) {
		var countryID = driver.findElement(By.id("countryID")).getAttribute("value");
		return new CountryProp(countryID, country.getTitle());
	}


	private Set<ShareProp> getSharesByCountry(List<MarketIndexDTO> marketIndexListDTO) throws Exception {
		
		Optional<MarketIndexDTO> marketIndexOfAllSharesByCountry = marketIndexListDTO.stream()
																					 .filter(isOverallMarketIndex)
																					 .findFirst();

		if (marketIndexOfAllSharesByCountry.isPresent()) {
			MarketIndexDTO marketIndex = marketIndexOfAllSharesByCountry.get();
			Set<ShareProp> sharesByCountry = marketIndex.getShares();
			return sharesByCountry;
		}

		throw new Exception();
	}
	
	private TreeSet<MarketIndexProp> getMarketIndexesByCountry(List<MarketIndexDTO> marketIndexListDTO) throws Exception {
		
		
		TreeSet<MarketIndexProp> marketIndexList = marketIndexListDTO.stream()
				 .map(MarketIndexDtoToMarketIndexProp)
				 .collect(Collectors.toCollection(()-> new TreeSet<MarketIndexProp>(MarketIndexProp.byTitle)));
		
		return marketIndexList;
	}
	
	public static Predicate<MarketIndexDTO> isOverallMarketIndex = (marketIndex) -> marketIndex.getPropierties()
			.getCode().equalsIgnoreCase("all");


	private void buildCountry(CountryProp country, RegionProp region, List<MarketIndexDTO> MarketIndexList) throws Exception {
		
		CountryProp countryToAdd = getCountryToSave(country, driver);

		CountryDTO coutry = countryService.findByCode(countryToAdd.getCode());

	    TreeSet<MarketIndexProp> marketIndexes = getMarketIndexesByCountry(MarketIndexList);
		 
		Set<ShareProp> shares = getSharesByCountry(MarketIndexList);

		if(country == null){
			createNewCountry(countryToAdd, region, shares, marketIndexes);
		}else{

			CompletableFuture<Boolean> a = regionService.updateCoverageRegion(region.getCode());
		}

		var countryId = countryToAdd.getCode();
		
		int coverage = getCountryCoverage(country.getCode(), shares, marketIndexes);
		
		CountryDTO newCountryDTO = CountryDTO.createNewCountry(countryId, countryToAdd, region, shares, marketIndexes);
		
		countryService.add(newCountryDTO);
		
		updateRegionCoverage(region, coverage);
		
	}

	private void createNewCountry(CountryProp countryProp, RegionProp region,
											Set<ShareProp> shares, TreeSet<MarketIndexProp> marketIndexes) {
		CountryDTO newCountry = CountryDTO.create(countryProp, region, shares, marketIndexes);
		countryService.add(newCountry);
	}
	//TODO: COVERAGE UPDATE CONSTRUCTOR in service
	private void updateCoverageAndPersist(CountryDTO countryToUpdate, CountryProp countryProp, RegionProp region, List<MarketIndexDTO> MarketIndexList) throws Exception {
		Set<ShareProp> shares = getSharesByCountry(MarketIndexList);
		TreeSet<MarketIndexProp> marketIndexes = getMarketIndexesByCountry(MarketIndexList);
		CountryDTO newCountry = CountryDTO.createNewCountry(countryProp.getCode(), countryProp, region, shares, marketIndexes);
		countryService.add(newCountry);
	}


	
	public int getCountryCoverage(String countryCode, Set<ShareProp> shares, TreeSet<MarketIndexProp> marketIndexes) {

		Optional<CoverageCountry> optionalCoverageCountry = coverageCountryService.findByCode(countryCode).stream().findFirst();
			
		if(optionalCoverageCountry.isPresent()) {
			CoverageCountry coverageCountry = optionalCoverageCountry.get();
			
			return (shares.size() * 100 ) / coverageCountry.getSharesTotalQuantity();
		}
		
		log.error("Not Coverage Country: {}",countryCode);
		return 0;

	}
	
	public void updateRegionCoverage(RegionProp region, int coverage) {
		
		Optional<RegionDTO> optionalRegion = regionService.findByCode(region.getCode()).stream().findFirst();
		
		if(optionalRegion.isPresent()) {
			RegionDTO regionDTO = optionalRegion.get();
			regionDTO.setCoverage(coverage);
			
			regionService.add(regionDTO);
		}

	}

	public void checkRegionIsCovered(List<RegionDTO> regions) throws Exception {
		if(regions.size() == 0) {
			String exceptionMessage = regionTitle != null ? String.format("Region: %s was covered", regionTitle)
					: "All Regions was Covered";
			throw new Exception(exceptionMessage);
		}
	}
*/
}
