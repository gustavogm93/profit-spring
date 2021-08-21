package dev.abel.springbootdocker.failed;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FailedRepository extends MongoRepository<FailedRegionDTO, String>{
		@Override
		List<FailedRegionDTO> findAll();
		
		
		@Query("{ 'region' : ?0 }")
		FailedRegionDTO findByRegionCode(String regionCode);
}
