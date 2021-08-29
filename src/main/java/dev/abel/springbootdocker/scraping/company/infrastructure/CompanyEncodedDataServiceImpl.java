package dev.abel.springbootdocker.scraping.company.infrastructure;

import dev.abel.springbootdocker.collections.country.CountryProp;
import dev.abel.springbootdocker.collections.region.RegionDTO;
import dev.abel.springbootdocker.collections.region.RegionService;
import dev.abel.springbootdocker.scraping.company.domain.CompanyEncodedData;
import dev.abel.springbootdocker.scraping.country.domain.CompanyEncodedDataS;
import dev.abel.springbootdocker.scraping.country.domain.EncodedData;
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
public class CompanyEncodedDataServiceImpl implements CompanyEncodedDataService {

    private final CompanyEncodedDataRepository companyEncodedDataRepository;

    private final EncodedData encodedData;

    private final MongoTemplate mongoTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CompanyEncodedDataServiceImpl.class);

    public CompanyEncodedDataServiceImpl(CompanyEncodedDataRepository companyEncodedDataRepository, RegionService regionService, MongoTemplate mongoTemplate) {
        this.companyEncodedDataRepository = companyEncodedDataRepository;
        this.regionService = regionService;
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

        Criteria columnCriteria = Criteria.where("country.title").is(title);

        query.addCriteria(columnCriteria);

        return this.mongoTemplate.find(query, CompanyEncodedData.class);
    }

    public void normalizeCompanyEncodedData() {
        List<CompanyEncodedData> CompanyEncodedDataSUnncompleted = new ArrayList<>();

        List<CompanyEncodedData> totalCompanyEncodedDataSL = getAll();
        if (totalCompanyEncodedDataSL.size() > 93) {
            logger.warn("Html was normalized");
            return;
        }


        List<RegionDTO> regions = regionService.getAll();
        for (RegionDTO region : regions) {
            @NonNull Set<CountryProp> countries = region.getCountries();

            for (CountryProp country : countries) {
                List<CompanyEncodedData> CompanyEncodedDataSL = findByTitle(country.getTitle());

                if (CompanyEncodedDataSL.isEmpty()) {
                    CompanyEncodedData CompanyEncodedDataS = new CompanyEncodedData(country, region.getProperties());
                    CompanyEncodedDataSUnncompleted.add(CompanyEncodedDataS);
                }

            }
        }
        ;
        companyEncodedDataRepository.saveAll(CompanyEncodedDataSUnncompleted);
        logger.info("Html its successful normalized");
    }

    public List<CompanyEncodedData> getHtmlUnncompleted(){
        Query query = new Query();

        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("encodeData").is(null),Criteria.where("error").ne(null));

        query.addCriteria(criteria);

        return this.mongoTemplate.find(query, CompanyEncodedData.class);
    }

}


