package dev.abel.springbootdocker.collections.region;

import java.util.List;

public interface RegionService {

	public List<RegionDTO> getAll();
	
	public List<RegionDTO> findByTitle(String title);
	
	public RegionDTO findByCode(String code) throws Exception;
	
	public void add(RegionDTO regionDTO);
	
	public void addAll(List<RegionDTO> regionDTOList);
	
	public void delete(String code);
	public void updateCoverageRegion(String regionCode) throws Exception;
}
