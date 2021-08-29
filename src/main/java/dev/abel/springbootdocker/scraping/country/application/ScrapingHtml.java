package dev.abel.springbootdocker.scraping.country.application;

import com.google.common.collect.ImmutableList;
import com.mongodb.lang.Nullable;
import dev.abel.springbootdocker.collections.country.CountryDTO;
import dev.abel.springbootdocker.collections.country.CountryProp;
import dev.abel.springbootdocker.collections.country.CountryService;
import dev.abel.springbootdocker.collections.marketIndex.MarketIndexDTO;
import dev.abel.springbootdocker.collections.marketIndex.MarketIndexProp;
import dev.abel.springbootdocker.collections.region.RegionDTO;
import dev.abel.springbootdocker.collections.region.RegionProp;
import dev.abel.springbootdocker.collections.region.RegionService;
import dev.abel.springbootdocker.collections.share.ShareProp;
import dev.abel.springbootdocker.generics.Property;
import dev.abel.springbootdocker.scraping.country.domain.*;
import dev.abel.springbootdocker.scraping.country.infrastructure.HtmlScrapedService;
import dev.abel.springbootdocker.scraping.country.infrastructure.HtmlScrapedServiceImpl;
import dev.abel.springbootdocker.scraping.selenium.InvestmentEquityPage;
import dev.abel.springbootdocker.utils.MapperUtils;
import dev.abel.springbootdocker.utils.Msg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class ScrapingHtml extends InvestmentEquityPage {

    private RegionService regionService;
    private CountryService countryService;
    private HtmlScrapedService htmlScrapedService;
    private MapperUtils mapperUtils;

    private static final Logger logger = LoggerFactory.getLogger(ScrapingHtml.class);

    public ScrapingHtml(RegionService regionService, CountryService countryService, HtmlScrapedService htmlScrapedService, MapperUtils mapperUtils) {
        this.regionService = regionService;
        this.countryService = countryService;
        this.htmlScrapedService = htmlScrapedService;
        this.mapperUtils = mapperUtils;
    }

    public void executor(@Nullable String regionTitle) throws Exception {

        startSeleniumDriver();
        setUpSeleniumDriver();

        List<HtmlScraped> htmlUncommpleted = htmlScrapedService.getHtmlUnncompleted();
        htmlUncommpleted = htmlUncommpleted.stream().filter(html -> html.getRegion().getTitle().equalsIgnoreCase(regionTitle)).collect(Collectors.toList());

        logger.info("Region: {} is Uncovered, go through scraping for get countries (market index, shares)", regionTitle);
        for (HtmlScraped htmlScraped: htmlUncommpleted) {
                try {
                    fetchProcess(htmlScraped);
                } catch (Exception e) {
                    logger.error("An error was occurred when scraping {}, error: {}",htmlScraped.getCountry(), e);
                    closeSeleniumDriver();
                    startSeleniumDriver();
                    setUpSeleniumDriver();
                }

        }
        driver.close();
    }

    private void fetchProcess(HtmlScraped htmlScraped) throws Exception {
        CountryProp country = htmlScraped.getCountry();
        String urlCountry = mapperUtils.generateUrl(country.getTitle());
        System.out.println(urlCountry);
        goToPage(urlCountry);
        List<EncodedMarketIndex> encodedMarketIndexList = new ArrayList<>();
        String codeCountry = getCountryCode();
        for (WebElement optionMarketIndex : getOptionsOfMarketIndex()) {
            logger.info(Msg.fetchingMarketIndex);

            String codeMarketIndex = getCodeMarketIndex(optionMarketIndex);
            String titleMarketIndex = getTitleMarketIndex(optionMarketIndex);

            optionMarketIndex.click();
            wait.until(checkForTableShares());

            List<EncodedShare> EncodedsharesList = getEncodedSharesByElement();

            EncodedMarketIndex encodedMarketIndex = new EncodedMarketIndex(titleMarketIndex, codeMarketIndex ,EncodedsharesList);
            encodedMarketIndexList.add(encodedMarketIndex);
        }

        EncodedCountry encodedCountry = new EncodedCountry(codeCountry, country.getTitle(),urlCountry);
        EncodedData encodedData = new EncodedData(encodedCountry, encodedMarketIndexList);

        boolean success = htmlScraped.fillHtmlScraped(encodedData);

        if(success){
            logger.info("extracted successful encoded data for country: {}", country.getTitle());
        }else{
            logger.warn("extracted failed encoded data for country: {}", country.getTitle());
            logger.error(htmlScraped.getError());
        }
        htmlScrapedService.add(htmlScraped);
    }


    private List<EncodedShare> getEncodedSharesByElement() {
        logger.info(Msg.fetchingShares);
        List<EncodedShare> encodedShareList = new ArrayList<>();

        for (WebElement shareElement : getShareElements()) {

            String shareTitle = getShareTitle(shareElement);
            String shareFullTitle = getShareTitle(shareElement);
            String shareCode = getShareCode(shareElement);
            String shareUrl = getShareUrl(shareElement);

            EncodedShare encodedShare = new EncodedShare(shareCode, shareTitle, shareFullTitle, shareUrl);
            encodedShareList.add(encodedShare);

        }
        logger.info(Msg.saveShares);
        return encodedShareList;
    }
}