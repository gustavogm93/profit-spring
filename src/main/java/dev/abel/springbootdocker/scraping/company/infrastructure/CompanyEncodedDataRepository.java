package dev.abel.springbootdocker.scraping.company.infrastructure;

import dev.abel.springbootdocker.scraping.company.domain.CompanyEncodedData;
import dev.abel.springbootdocker.scraping.country.domain.HtmlScraped;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyEncodedDataRepository extends MongoRepository<CompanyEncodedData, String> {

}
