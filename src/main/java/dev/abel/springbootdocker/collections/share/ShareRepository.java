package dev.abel.springbootdocker.collections.share;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends MongoRepository<ShareDTO, String>{
}
