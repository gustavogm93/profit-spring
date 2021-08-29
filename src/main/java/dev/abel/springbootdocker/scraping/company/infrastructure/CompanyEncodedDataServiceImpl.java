package dev.abel.springbootdocker.scraping.company.infrastructure;

import dev.abel.springbootdocker.collections.country.CountryProp;
import dev.abel.springbootdocker.collections.region.RegionDTO;
import dev.abel.springbootdocker.collections.region.RegionService;
import dev.abel.springbootdocker.scraping.company.domain.CompanyEncodedData;
import dev.abel.springbootdocker.scraping.company.domain.Location;
import dev.abel.springbootdocker.scraping.country.domain.EncodedData;
import dev.abel.springbootdocker.scraping.country.domain.EncodedShare;
import dev.abel.springbootdocker.scraping.country.domain.HtmlScraped;
import dev.abel.springbootdocker.scraping.country.infrastructure.HtmlScrapedService;
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
import java.util.stream.Collectors;

@Service
public class CompanyEncodedDataServiceImpl implements CompanyEncodedDataService {

    private final CompanyEncodedDataRepository companyEncodedDataRepository;

    private final HtmlScrapedService htmlScrapedService;

    private final MongoTemplate mongoTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CompanyEncodedDataServiceImpl.class);

    public CompanyEncodedDataServiceImpl(CompanyEncodedDataRepository companyEncodedDataRepository, HtmlScrapedService htmlScrapedService, MongoTemplate mongoTemplate) {
        this.companyEncodedDataRepository = companyEncodedDataRepository;
        this.htmlScrapedService = htmlScrapedService;
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



    public void normalizeCompanyEncodedDataByRegion(String region) throws Exception {
        List<CompanyEncodedData> CompanyEncodedDataSUnncompleted = new ArrayList<>();

        /*TODO CHANGE FOR REAL NUMBER TOTAL
        if (totalCompanyEncodedDataSL.size() > 93) {
            logger.warn("Html was normalized");
            return;
        }*/

        List<HtmlScraped> htmls = htmlScrapedService.getByRegion(region).stream().filter(html -> html.getEncodeData() != null).collect(Collectors.toList());

        for (HtmlScraped html : htmls) {
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
        logger.info("companyEncodedData its successful normalized");
    }


    public List<CompanyEncodedData> getHtmlUnncompleted() {
        Query query = new Query();

        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("encodeData").is(null), Criteria.where("error").ne(null));

        query.addCriteria(criteria);

        return this.mongoTemplate.find(query, CompanyEncodedData.class);
    }

}


