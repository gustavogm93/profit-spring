package dev.abel.springbootdocker.scraping.company.application;

import dev.abel.springbootdocker.scraping.company.domain.CompanyEncodedData;
import dev.abel.springbootdocker.scraping.company.domain.Location;
import dev.abel.springbootdocker.scraping.company.infrastructure.CompanyEncodedDataRepository;
import dev.abel.springbootdocker.scraping.country.domain.CountryScrapedData;
import dev.abel.springbootdocker.scraping.country.domain.EncodedShare;
import dev.abel.springbootdocker.scraping.country.application.CountryScrapedDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyEncodedDataServiceImpl implements CompanyEncodedDataService {

    private final CompanyEncodedDataRepository companyEncodedDataRepository;

    private final CountryScrapedDataService countryScrapedDataService;

    private final MongoTemplate mongoTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CompanyEncodedDataServiceImpl.class);

    public CompanyEncodedDataServiceImpl(CompanyEncodedDataRepository companyEncodedDataRepository, CountryScrapedDataService countryScrapedDataService, MongoTemplate mongoTemplate) {
        this.companyEncodedDataRepository = companyEncodedDataRepository;
        this.countryScrapedDataService = countryScrapedDataService;
        this.mongoTemplate = mongoTemplate;
    }

    public List<CompanyEncodedData> getAll() {
        return companyEncodedDataRepository.findAll();
    }

    public void add(CompanyEncodedData CompanyEncodedDataS) {
        companyEncodedDataRepository.save(CompanyEncodedDataS);
    }

    public List<CompanyEncodedData> findByTitle(String title) {
        Query query = new Query();

        Criteria columnCriteria = Criteria.where("title").is(title);

        query.addCriteria(columnCriteria);

        return this.mongoTemplate.find(query, CompanyEncodedData.class);
    }

    public List<CompanyEncodedData> findByCode(String code) {
        Query query = new Query();

        Criteria columnCriteria = Criteria.where("code").is(code);

        query.addCriteria(columnCriteria);

        return this.mongoTemplate.find(query, CompanyEncodedData.class);
    }


    public void normalize(String region) throws Exception {
        List<CompanyEncodedData> CompanyEncodedDataSUnncompleted = new ArrayList<>();

        /*TODO CHANGE FOR REAL NUMBER TOTAL
        if (totalCompanyEncodedDataSL.size() > 93) {
            logger.warn("Html was normalized");
            return;
        }*/

        List<CountryScrapedData> htmls = countryScrapedDataService.getByRegion(region);

        for (CountryScrapedData html : htmls) {
            List<EncodedShare> shares = html.getEncodeData().getAllSharesMarketIndex().getShares();

            for(EncodedShare share : shares) {

                String countryTitle = html.getCountry().getTitle();
                String countryCode = html.getCountry().getCode();
                String regionTitle = html.getRegion().getTitle();
                String regionCode = html.getRegion().getCode();

                Location location = new Location(countryTitle, countryCode, regionTitle, regionCode);
                CompanyEncodedData CompanyEncodedDataS = new CompanyEncodedData(share.getTitle(),share.getCode(), location);

                companyEncodedDataRepository.save(CompanyEncodedDataS);
            }

        }

        companyEncodedDataRepository.saveAll(CompanyEncodedDataSUnncompleted);
        logger.info("Html its successful normalized");
    }


    public List<CompanyEncodedData> getHtmlUnncompleted() {
        Query query = new Query();

        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("encodeData").is(null), Criteria.where("error").ne(null));

        query.addCriteria(criteria);

        return this.mongoTemplate.find(query, CompanyEncodedData.class);
    }

}


