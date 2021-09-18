package dev.abel.springbootdocker.scraping.company.infrastructure;

import dev.abel.springbootdocker.scraping.company.domain.CompanyScrapedData;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyEncodedDataRepository extends MongoRepository<CompanyScrapedData, String> {

}
