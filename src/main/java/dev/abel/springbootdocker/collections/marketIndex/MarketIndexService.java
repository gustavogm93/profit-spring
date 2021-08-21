package dev.abel.springbootdocker.collections.marketIndex;

import java.util.List;

public interface MarketIndexService {

	public List<MarketIndexDTO> getAll();

	public void add(MarketIndexDTO marketIndexDTO);
	
	public void addAll(List<MarketIndexDTO> marketIndexList);
	
	public void delete(String code);
	
	public List<MarketIndexDTO> findByTitle(String title);
	
}