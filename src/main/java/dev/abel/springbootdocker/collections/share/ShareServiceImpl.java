package dev.abel.springbootdocker.collections.share;


import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ShareServiceImpl implements ShareService {

	private final ShareRepository ShareRepository;

	private final MongoTemplate mongoTemplate;

	
	public List<ShareDTO> getAll() {
		return ShareRepository.findAll();
	}

	public void add(ShareDTO ShareDTO) {
		ShareRepository.save(ShareDTO);
	}
	
	public void addAll(List<ShareDTO> ShareDTOList) {
		ShareRepository.saveAll(ShareDTOList);
	}
	
	public void delete(String code) {
		if(!ShareRepository.existsById(code)) {
			throw new ShareNotFoundException(
                    "Share index with id " + code + " does not exists");
		}
			
		ShareRepository.deleteById(code);
	}
	
	public List<ShareDTO> findByTitle(String title) {
		Query query = new Query();
		
		Criteria columnCriteria = Criteria.where("properties.title").is(title);
		
		query.addCriteria(columnCriteria);
		
		return this.mongoTemplate.find(query, ShareDTO.class);
	}
}