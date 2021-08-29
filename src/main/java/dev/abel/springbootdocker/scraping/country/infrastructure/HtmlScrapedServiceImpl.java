package dev.abel.springbootdocker.scraping.country.infrastructure;

import dev.abel.springbootdocker.collections.country.CountryDTO;
import dev.abel.springbootdocker.collections.country.CountryProp;
import dev.abel.springbootdocker.collections.region.RegionDTO;
import dev.abel.springbootdocker.collections.region.RegionService;
import dev.abel.springbootdocker.collections.region.RegionServiceImpl;
import dev.abel.springbootdocker.scraping.country.domain.DataSource;
import dev.abel.springbootdocker.scraping.country.domain.HtmlScraped;
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
public class HtmlScrapedServiceImpl implements HtmlScrapedService {

    private final HtmlScrapedRepository HtmlScrapedRepository;

    private final RegionService regionService;

    private final MongoTemplate mongoTemplate;

    private static final Logger logger = LoggerFactory.getLogger(HtmlScrapedServiceImpl.class);

    public HtmlScrapedServiceImpl(HtmlScrapedRepository HtmlScrapedRepository, RegionService regionService, MongoTemplate mongoTemplate) {
        this.HtmlScrapedRepository = HtmlScrapedRepository;
        this.regionService = regionService;
        this.mongoTemplate = mongoTemplate;
    }

    public List<HtmlScraped> getAll() {
        return HtmlScrapedRepository.findAll();
    }

    public void add(HtmlScraped HtmlScraped) {
        HtmlScrapedRepository.save(HtmlScraped);
    }

    public List<HtmlScraped> findByTitle(String title) {
        Query query = new Query();

        Criteria columnCriteria = Criteria.where("country.title").is(title);

        query.addCriteria(columnCriteria);

        return this.mongoTemplate.find(query, HtmlScraped.class);
    }

    public List<HtmlScraped> getByRegion(String regionTitle) {
        Query query = new Query();

        Criteria columnCriteria = Criteria.where("region.title").is(regionTitle);

        query.addCriteria(columnCriteria);

        return this.mongoTemplate.find(query, HtmlScraped.class);
    }



    public void normalizeHtmlScraped() {
        List<HtmlScraped> htmlScrapedUnncompleted = new ArrayList<>();

        List<HtmlScraped> totalHtmlScrapedL = getAll();
        if (totalHtmlScrapedL.size() > 93) {
            logger.warn("Html was normalized");
            return;
        }


        List<RegionDTO> regions = regionService.getAll();
        for (RegionDTO region : regions) {
            @NonNull Set<CountryProp> countries = region.getCountries();

            for (CountryProp country : countries) {
                List<HtmlScraped> htmlScrapedL = findByTitle(country.getTitle());

                if (htmlScrapedL.isEmpty()) {
                    HtmlScraped htmlScraped = new HtmlScraped(country, region.getProperties());
                    htmlScrapedUnncompleted.add(htmlScraped);
                }

            }
        }
        ;
        HtmlScrapedRepository.saveAll(htmlScrapedUnncompleted);
        logger.info("Html its successful normalized");
    }

    public List<HtmlScraped> getHtmlUnncompleted(){
        Query query = new Query();

        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("encodeData").is(null),Criteria.where("error").ne(null));

        query.addCriteria(criteria);

        return this.mongoTemplate.find(query, HtmlScraped.class);
    }

}


