package dev.abel.springbootdocker.collections.country;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends MongoRepository<CountryDTO, String>{}
