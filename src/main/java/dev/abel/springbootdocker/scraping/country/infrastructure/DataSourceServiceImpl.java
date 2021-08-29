package dev.abel.springbootdocker.scraping.country.infrastructure;

import dev.abel.springbootdocker.collections.country.CountryProp;
import dev.abel.springbootdocker.collections.region.RegionDTO;
import dev.abel.springbootdocker.collections.region.RegionService;
import dev.abel.springbootdocker.scraping.country.domain.DataSource;
import dev.abel.springbootdocker.scraping.country.domain.HtmlScraped;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DataSourceServiceImpl implements DataSourceService {

    private final DataSourceRepository dataSourceRepository;
    private final RegionService regionService;
    private final MongoTemplate mongoTemplate;
    private static final Logger logger = LoggerFactory.getLogger(DataSourceServiceImpl.class);
    private static final Integer totalCountries = 93;
    @Autowired
    public DataSourceServiceImpl(DataSourceRepository dataSourceRepository, RegionService regionService, MongoTemplate mongoTemplate) {
        this.dataSourceRepository = dataSourceRepository;
        this.regionService = regionService;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<DataSource> getAll() {
        return dataSourceRepository.findAll();
    }

    @Override
    public void add(DataSource dataSource) {
        dataSourceRepository.save(dataSource);
    }

    @Override
    public void addAll(List<DataSource> dataSources) {
        dataSourceRepository.saveAll(dataSources);
    }


    @Override
    public List<DataSource> findByTitle(String title) {
        Query query = new Query();

        Criteria columnCriteria = Criteria.where("country.title").is(title);

        query.addCriteria(columnCriteria);

        return this.mongoTemplate.find(query, DataSource.class);
    }

    public void normalizeDataSource(){
        List<DataSource> dataSourceUnncompleted = new ArrayList<>();

        List<DataSource> totaldataSource = getAll();
        if(totaldataSource.size() > 93){
            logger.warn("DataSource was normalized");
            return;
        }

        List<RegionDTO> regions = regionService.getAll();
        for(RegionDTO region : regions) {
            @NonNull Set<CountryProp> countries = region.getCountries();

            for(CountryProp country : countries){
                List<DataSource> dataSourceL = findByTitle(country.getTitle());

                if(dataSourceL.isEmpty()){
                    DataSource dataSource = new DataSource(country, region.getProperties());
                    dataSourceUnncompleted.add(dataSource);
                }

            }
        };
        dataSourceRepository.saveAll(dataSourceUnncompleted);
        logger.info("DataSource its successful normalized");
    }

}
