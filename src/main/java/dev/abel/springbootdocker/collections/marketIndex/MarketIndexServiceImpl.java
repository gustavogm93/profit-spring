package dev.abel.springbootdocker.collections.marketIndex;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MarketIndexServiceImpl implements MarketIndexService {

	private final MarketIndexRepository marketIndexRepository;

	private final MongoTemplate mongoTemplate;

	
	public List<MarketIndexDTO> getAll() {
		return marketIndexRepository.findAll();
	}

	public void add(MarketIndexDTO marketIndex) {
		marketIndexRepository.save(marketIndex);
	}
	
	public void addAll(List <MarketIndexDTO> marketIndexList) {
		marketIndexRepository.saveAll(marketIndexList);
	}
	
	public void delete(String code) {
		if(!marketIndexRepository.existsById(code)) {
			throw new MarketIndexNotFoundException(
                    "Market index with id " + code + " does not exists");
		}
			
		marketIndexRepository.deleteById(code);
	}
	
	public List<MarketIndexDTO> findByTitle(String title) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.title").is(title);
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, MarketIndexDTO.class);
	}

	
}
