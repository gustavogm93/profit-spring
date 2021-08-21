package dev.abel.springbootdocker.collections.country;

import com.google.common.collect.ImmutableList;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CountryService {

	public List<CountryDTO> getAll();

	public void add(CountryDTO countryDTO);

	public void addAndUpdate(CountryDTO country) throws Exception;

	public void addAll(List<CountryDTO> countryDTO);
	
	public void delete(String code);
			
	public List<CountryDTO> findByTitle(String title);

	public List<CountryDTO> getCountriesCoveredByRegion(String region) throws Exception;

	public ImmutableList<CountryDTO> getCountriesUncoveredByRegion(String region) throws Exception;

	public ImmutableList<CountryDTO> getCountriesUncoveredFromRegions(List<String> regions);

	public List<CountryDTO> getCountriesByRegion(String regionCode);

	public CountryDTO findByCode(String code) throws Exception;

	public void testTransaction(CountryDTO country) throws Exception;
}
