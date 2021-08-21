package dev.abel.springbootdocker.collections.summary;

import java.util.List;

public interface SummaryScrapingRegionService {

	public List<SummaryScrapingRegion> getAll();

	public void add(SummaryScrapingRegion summaryScrapingRegion);
	
	public void addAll(List<SummaryScrapingRegion> summaryScrapingRegionList);
	
	public void delete(String code);
	
	public List<SummaryScrapingRegion> findByTitle(String title);
	
}
