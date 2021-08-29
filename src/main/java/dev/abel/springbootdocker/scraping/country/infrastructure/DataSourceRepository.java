package dev.abel.springbootdocker.scraping.country.infrastructure;

import dev.abel.springbootdocker.scraping.country.domain.DataSource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSourceRepository extends MongoRepository<DataSource, String> {

}