package dev.abel.springbootdocker.collections.country;

import dev.abel.springbootdocker.collections.region.RegionService;
import dev.abel.springbootdocker.utils.Validates;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class CountryServiceImpl implements CountryService {

	private final CountryRepository countryRepository;

	private final RegionService regionService;

	private final MongoTemplate mongoTemplate;

	private final Validates validate;

	@Autowired
	public CountryServiceImpl(CountryRepository countryRepository, @Lazy RegionService regionService, MongoTemplate mongoTemplate, Validates validate) {
		this.countryRepository = countryRepository;
		this.regionService = regionService;
		this.mongoTemplate = mongoTemplate;
		this.validate = validate;
	}

	public List<CountryDTO> getAll() {
		return countryRepository.findAll();
	}

	@Async
	public void add(CountryDTO country){
		countryRepository.save(country);
	}

	public void testTransaction(CountryDTO country) throws Exception {
		int i = 2;
		countryRepository.save(country);
		/*if(i + 1 == 3)
			throw new Exception("ERROR");*/

		Optional<CountryDTO> a = countryRepository.findById(country.getId());
		System.out.println(a);

	}


	@Async
	public void addAndUpdate(CountryDTO country) throws Exception {
		countryRepository.save(country);
		regionService.updateCoverageRegion(country.getRegion().getCode());

	}

	@Async
	public void addAll(List<CountryDTO> countryDTO) {
		countryRepository.saveAll(countryDTO);
	}

	@Async
	public void delete(String code) {
		if(!countryRepository.existsById(code)) {
			throw new CountryNotFoundException(
                    "Country with id " + code + " does not exists");
		}
			
		countryRepository.deleteById(code);
	}
	
	public List<CountryDTO> findByTitle(String title) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.title").is(title);
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, CountryDTO.class);
	}

	public CountryDTO findByCode(String code) throws Exception {
		Query query = new Query();

		Criteria columnCriteria = Criteria.where("properties.code").is(code);

		query.addCriteria(columnCriteria);

		List<CountryDTO> country = this.mongoTemplate.find(query, CountryDTO.class);

		if(country.size() == 0)
			return null;

		if(country.size() == 1)
			return country.get(0);

		throw new Exception("There are more than one country with same code");

	}

	public List<CountryDTO> getCountriesByRegion(String regionCode) {
		Query query = new Query();

		Criteria isRegionCriteria = Criteria.where("region.code").is(regionCode);
		query.addCriteria(isRegionCriteria);

		return this.mongoTemplate.find(query, CountryDTO.class);
	}

	public List<CountryDTO> getCountriesCoveredByRegion(String region) throws Exception {
		Query query = new Query();
		region = region.toLowerCase();

		if(!validate.isValidRegion(region))
			throw new Exception("Invalid region");

		Criteria isRegionCriteria = Criteria.where("region.code").is(region);
		Criteria isCoveredCriteria = Criteria.where("coverage.isCovered").is(true);

		query.addCriteria(isRegionCriteria);
		query.addCriteria(isCoveredCriteria);

		return this.mongoTemplate.find(query, CountryDTO.class);
	}

	public ImmutableList<CountryDTO> getCountriesUncoveredByRegion(String region) throws Exception {
		Query query = new Query();
		region = region.toLowerCase();

		if(!validate.isValidRegion(region))
			throw new Exception("Invalid region");

		Criteria isRegionCriteria = Criteria.where("region.code").is(region);
		Criteria isCoveredCriteria = Criteria.where("coverage.isCovered").is(false);

		query.addCriteria(isRegionCriteria);
		query.addCriteria(isCoveredCriteria);

		return ImmutableList.copyOf(this.mongoTemplate.find(query, CountryDTO.class));
	}

	public ImmutableList<CountryDTO> getCountriesUncoveredFromRegions(List<String> regions) {
	if(regions.size() == 0)
		return ImmutableList.copyOf(Collections.emptyList());

			Query query = new Query();

			Criteria regionsAndUncoveragedCriteria = Criteria.where("region.code").in(regions).and("coverage.isCovered").is(false);

			query.addCriteria(regionsAndUncoveragedCriteria);
			return ImmutableList.copyOf(this.mongoTemplate.find(query, CountryDTO.class));
	}
	

}
