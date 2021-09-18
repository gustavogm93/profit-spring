package dev.abel.springbootdocker.scraping.company.application;

import dev.abel.springbootdocker.scraping.company.domain.CompanyScrapedData;
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
import java.util.stream.Collectors;

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

    public List<CompanyScrapedData> getAll() {
        return companyEncodedDataRepository.findAll();
    }

    public void add(CompanyScrapedData CompanyEncodedDataS) {
        companyEncodedDataRepository.save(CompanyEncodedDataS);
    }

    public List<CompanyScrapedData> findByTitle(String title) {
        Query query = new Query();

        Criteria columnCriteria = Criteria.where("title").is(title);

        query.addCriteria(columnCriteria);

        return this.mongoTemplate.find(query, CompanyScrapedData.class);
    }

    public List<CompanyScrapedData> findByCode(String code) {
        Query query = new Query();

        Criteria columnCriteria = Criteria.where("code").is(code);

        query.addCriteria(columnCriteria);

        return this.mongoTemplate.find(query, CompanyScrapedData.class);
    }


    public void normalize(String region) throws Exception {
        List<CompanyScrapedData> CompanyEncodedDataSUnncompleted = new ArrayList<>();

        /*TODO CHANGE FOR REAL NUMBER TOTAL
        if (totalCompanyEncodedDataSL.size() > 93) {
            logger.warn("Html was normalized");
            return;
        }*/

        List<CountryScrapedData> htmls = countryScrapedDataService.getByRegion(region);
        htmls = htmls.stream().filter(html-> html.getEncodeData() != null).collect(Collectors.toList());

        for (CountryScrapedData html : htmls) {
            List<EncodedShare> shares = html.getEncodeData().getAllSharesMarketIndex().getShares();

            for(EncodedShare share : shares) {

                String countryTitle = html.getCountry().getTitle();
                String countryCode = html.getCountry().getCode();
                String regionTitle = html.getRegion().getTitle();
                String regionCode = html.getRegion().getCode();

                Location location = new Location(countryTitle, countryCode, regionTitle, regionCode);
                CompanyScrapedData CompanyEncodedDataS = new CompanyScrapedData(share.getTitle(),share.getCode(), location);
                CompanyEncodedDataS.generateUrls(share.getUrl());
                companyEncodedDataRepository.save(CompanyEncodedDataS);
            }

        }

        companyEncodedDataRepository.saveAll(CompanyEncodedDataSUnncompleted);
        logger.info("Company is successful normalized");

    }



    public List<CompanyScrapedData> getUncommpletedByRegion(String regionTitle) {
        Query query = new Query();

        Criteria columnCriteria = Criteria.where("location.regionTitle").is(regionTitle);
        columnCriteria.orOperator(Criteria.where("encodeData").is(null), Criteria.where("error").ne(null));
        System.out.println(columnCriteria);
        query.addCriteria(columnCriteria);

        return this.mongoTemplate.find(query, CompanyScrapedData.class);
    }

}


