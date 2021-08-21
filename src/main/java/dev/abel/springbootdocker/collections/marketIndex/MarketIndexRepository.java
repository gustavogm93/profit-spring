package dev.abel.springbootdocker.collections.marketIndex;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MarketIndexRepository  extends MongoRepository<MarketIndexDTO, String>{}
