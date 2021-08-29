package dev.abel.springbootdocker.scraping.country.infrastructure;

import dev.abel.springbootdocker.collections.country.CountryDTO;
import dev.abel.springbootdocker.collections.country.CountryProp;
import dev.abel.springbootdocker.collections.region.RegionDTO;
import dev.abel.springbootdocker.collections.region.RegionService;
import dev.abel.springbootdocker.collections.region.RegionServiceImpl;
import dev.abel.springbootdocker.scraping.country.domain.DataSource;
import dev.abel.springbootdocker.scraping.country.domain.CountryScrapedData;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CountryScrapedDataServiceImpl implements CountryScrapedDataService {

    private final CountryScrapedDataRepository CountryScrapedDataRepository;

    private final RegionService regionService;

    private final MongoTemplate mongoTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CountryScrapedDataServiceImpl.class);

    public CountryScrapedDataServiceImpl(CountryScrapedDataRepository CountryScrapedDataRepository, RegionService regionService, MongoTemplate mongoTemplate) {
        this.CountryScrapedDataRepository = CountryScrapedDataRepository;
        this.regionService = regionService;
        this.mongoTemplate = mongoTemplate;
    }

    public List<CountryScrapedData> getAll() {
        return CountryScrapedDataRepository.findAll();
    }

    public void add(CountryScrapedData CountryScrapedData) {
        CountryScrapedDataRepository.save(CountryScrapedData);
    }

    public List<CountryScrapedData> findByTitle(String title) {
        Query query = new Query();

        Criteria columnCriteria = Criteria.where("country.title").is(title);

        query.addCriteria(columnCriteria);

        return this.mongoTemplate.find(query, CountryScrapedData.class);
    }

    public List<CountryScrapedData> getByRegion(String regionTitle) {
        Query query = new Query();

        Criteria columnCriteria = Criteria.where("region.title").is(regionTitle);

        query.addCriteria(columnCriteria);

        return this.mongoTemplate.find(query, CountryScrapedData.class);
    }



    public void normalizeCountryScrapedData() {
        List<CountryScrapedData> CountryScrapedDataUnncompleted = new ArrayList<>();

        List<CountryScrapedData> totalCountryScrapedDataL = getAll();
        if (totalCountryScrapedDataL.size() > 93) {
            logger.warn("Html was normalized");
            return;
        }


        List<RegionDTO> regions = regionService.getAll();
        for (RegionDTO region : regions) {
            @NonNull Set<CountryProp> countries = region.getCountries();

            for (CountryProp country : countries) {
                List<CountryScrapedData> CountryScrapedDataL = findByTitle(country.getTitle());

                if (CountryScrapedDataL.isEmpty()) {
                    CountryScrapedData CountryScrapedData = new CountryScrapedData(country, region.getProperties());
                    CountryScrapedDataUnncompleted.add(CountryScrapedData);
                }

            }
        }
        ;
        CountryScrapedDataRepository.saveAll(CountryScrapedDataUnncompleted);
        logger.info("Html its successful normalized");
    }

    public List<CountryScrapedData> getHtmlUnncompleted(){
        Query query = new Query();

        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("encodeData").is(null),Criteria.where("error").ne(null));

        query.addCriteria(criteria);

        return this.mongoTemplate.find(query, CountryScrapedData.class);
    }

}


