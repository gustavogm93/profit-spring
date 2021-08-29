package dev.abel.springbootdocker.scraping.country.application;

import com.google.common.collect.ImmutableList;
import com.mongodb.lang.Nullable;
import dev.abel.springbootdocker.collections.country.CountryDTO;
import dev.abel.springbootdocker.collections.country.CountryService;
import dev.abel.springbootdocker.collections.marketIndex.MarketIndexService;
import dev.abel.springbootdocker.collections.region.RegionDTO;
import dev.abel.springbootdocker.collections.region.RegionService;
import dev.abel.springbootdocker.scraping.selenium.InvestmentEquityPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static dev.abel.springbootdocker.collections.region.RegionDTO.byNotCoveraged;
//TODO: SOLO SCRAPEA LOS DATOS VERDADES DEL PAIS
public class ScrapingDataSource extends InvestmentEquityPage  {
/*
    private RegionService regionService;

    private CountryService countryService;

    private MarketIndexService marketIndexService;

    private static final Logger log = LoggerFactory.getLogger(ScrapingCountryStrategy.class);

    private ScrapingDataSource(RegionService regionService, MarketIndexService marketIndexService,
                                    CountryService countryService) {
        this.regionService = regionService;
        this.marketIndexService = marketIndexService;
        this.countryService = countryService;
    }
//Region -> Countries filter (dataSource complete ) -> countries.
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

    //agarrar datos
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

*/
}
