package dev.abel.springbootdocker.collections.company;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompanyRepository extends MongoRepository<CompanyDTO, String>{

}
