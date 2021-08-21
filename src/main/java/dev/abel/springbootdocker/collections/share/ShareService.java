package dev.abel.springbootdocker.collections.share;

import java.util.List;

public interface ShareService {

	public List<ShareDTO> getAll();

	public void add(ShareDTO ShareDTO);
	
	public void addAll(List<ShareDTO> ShareDTOList);
	
	public void delete(String code);
	
	public List<ShareDTO> findByTitle(String title);
	
}
