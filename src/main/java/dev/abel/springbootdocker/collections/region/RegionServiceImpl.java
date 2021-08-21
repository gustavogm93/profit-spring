package dev.abel.springbootdocker.collections.region;

import dev.abel.springbootdocker.collections.country.CountryDTO;
import dev.abel.springbootdocker.collections.country.CountryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

	private final RegionRepository regionRepository;

	private final CountryService countryService;

	private final MongoTemplate mongoTemplate;

	private static final Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);

	@Autowired
	public RegionServiceImpl(RegionRepository regionRepository, @Lazy CountryService countryService, MongoTemplate mongoTemplate) {
		this.regionRepository = regionRepository;
		this.countryService = countryService;

		this.mongoTemplate = mongoTemplate;
	}

	@Async
	public List<RegionDTO> getAll() {
		return regionRepository.findAll();
	}
	@Async
	public void add(RegionDTO regionDTO) {
		regionRepository.save(regionDTO);
	}
	@Async
	public void addAll(List<RegionDTO> regionDTO) {
		regionRepository.saveAll(regionDTO);
	}
	@Async
	public void delete(String code) {
		if (!regionRepository.existsById(code)) {
			throw new RegionNotFoundException("Region with id " + code + " does not exists");
		}

		regionRepository.deleteById(code);
	}

	@Async
	public List<RegionDTO> findByTitle(String title) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.title").regex(title,"i");
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, RegionDTO.class);
	}

	@Async
	public RegionDTO findByCode(String code) throws Exception {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.code").is(code);

		query.addCriteria(columnCriteria);

		List<RegionDTO> regions =  this.mongoTemplate.find(query, RegionDTO.class);
		if(regions.size() > 1)
			throw new Exception("There are more than one region with same code: " + code);

		if(regions.size() == 1)
			return regions.get(0);

			throw new Exception("There not exist region with code: " + code + " first scrap it with GET /region");

	}

	@Async
	public void updateCoverageRegion(String regionCode) throws Exception {
		try {
			List<CountryDTO> countries = countryService.getCountriesByRegion(regionCode);
			int coverageCountryPercentage = 0;
			for (int i = 0; i < countries.size(); i++) {
				coverageCountryPercentage = +countries.get(i).getCoverage().getTotalCoverage();
			}
			int totalCoverageRegion = coverageCountryPercentage / countries.size();

			RegionDTO region = this.findByCode(regionCode);

			region.updateCoverage(totalCoverageRegion);

		} catch (Exception e) {
			logger.error(e.getMessage());
				throw new Exception(e);
			};
		}
}


