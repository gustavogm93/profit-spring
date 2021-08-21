package dev.abel.springbootdocker.scraping;

import dev.abel.springbootdocker.collections.country.CountryDTO;
import dev.abel.springbootdocker.collections.country.CountryProp;
import dev.abel.springbootdocker.collections.country.CountryService;
import dev.abel.springbootdocker.collections.region.RegionDTO;
import dev.abel.springbootdocker.collections.region.RegionProp;
import dev.abel.springbootdocker.collections.region.RegionService;
import dev.abel.springbootdocker.enums.RegionConstant;
import dev.abel.springbootdocker.enums.utils.Url;
import dev.abel.springbootdocker.scraping.jsoup.JsoupBase;
import com.google.common.collect.ImmutableList;
import org.apache.poi.ss.formula.functions.Count;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.synth.Region;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class ScrapingRegionStrategy implements JsoupBase {

	private static final Logger logger = LoggerFactory.getLogger(ScrapingRegionStrategy.class);
	private static final String urlEquities = Url.equities;
	
	private final RegionService regionService;

	private final CountryService countryService;

	@Autowired
	private ScrapingRegionStrategy(RegionService regionService, CountryService countryService) {
		this.regionService = regionService;
		this.countryService = countryService;
	}

	public void executor() throws Exception, IOException {
		logger.info("Getting regionDTO");

		Document document = getDocument(urlEquities);

		ImmutableList<RegionConstant> regions = getConstantsToFetch();

		regions.forEach((region) -> {

				try {

					Elements countriesElements = getCountriesElementsInDocument(document, region);

					Set<CountryProp> countries = elementsToCountrySet(countriesElements);

					RegionProp regionProps = new RegionProp(region.getCode(), region.getTitle());

					/*countries.stream().map(countryProp -> CountryDTO.createNewCountry(countryProp, regionProps))
									  .forEach(countryService::testTransaction);*/
					int i = 0;
					for (CountryProp country : countries) {
						i++;
					if(i == 1) {
						CountryDTO ctr = CountryDTO.createNewCountry(country, regionProps);
						countryService.testTransaction(ctr);
						break;
					}
					}

					RegionDTO regionDTO = RegionDTO.createRegion(region.getCode(), regionProps, countries);

					regionService.add(regionDTO);

					logger.info("Save regionDTO");
				} catch (Exception e) {
					logger.error(e.getMessage());

				}


		});

	}


	public Document getDocument(String urlEquities) throws IOException {
		Document doc;
		try {
			doc = Jsoup.connect(urlEquities).get();
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return doc;
	}

	
	private ImmutableList<RegionConstant> getConstantsToFetch() {
		return RegionConstant.values;
	}

	private Elements getCountriesElementsInDocument(Document data, RegionConstant region) {

		String idElement = String.format("#cdregion%s", region.getCode());
		return  data.select(idElement)
				.first()
				.select("span[data-country-name]");
	}
	
	private TreeSet<CountryProp> elementsToCountrySet(Elements elements) {
		
		return elements.stream()
				 .map(this::getTitleAndCodeCountry)
			     .collect(Collectors.toCollection(()-> new TreeSet<>(CountryProp.byCode)));
	}


	private CountryProp getTitleAndCodeCountry (Element element){

			String titleCountry = element.attr("data-country-name");

			String codeCountry = element.attr("data-cid");

			return new CountryProp(codeCountry, titleCountry);
		}

	}





