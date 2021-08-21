package dev.abel.springbootdocker.collections.summary;

import dev.abel.springbootdocker.collections.share.ShareNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SummaryScrapingRegionServiceImpl implements SummaryScrapingRegionService {

	private final SummaryScrapingRegionRepository summaryScrapingRegionRepository;

	private final MongoTemplate mongoTemplate;

	
	public List<SummaryScrapingRegion> getAll() {
		return summaryScrapingRegionRepository.findAll();
	}

	public void add(SummaryScrapingRegion ShareDTO) {
		summaryScrapingRegionRepository.save(ShareDTO);
	}
	
	public void addAll(List<SummaryScrapingRegion> ShareDTOList) {
		summaryScrapingRegionRepository.saveAll(ShareDTOList);
	}
	
	public void delete(String code) {
		if(!summaryScrapingRegionRepository.existsById(code)) {
			throw new ShareNotFoundException(
                    "Share index with id " + code + " does not exists");
		}
			
		summaryScrapingRegionRepository.deleteById(code);
	}
	
	public List<SummaryScrapingRegion> findByTitle(String title) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.title").is(title);
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, SummaryScrapingRegion.class);
	}

}
