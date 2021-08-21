package dev.abel.springbootdocker.collections.region;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
public class CoverageRegion {

	@Id
	private String id;

	@Field( "properties")
	@NonNull private RegionProp properties;
	
	@Field( "countries")
	private Integer totalCountries;

	@Field( "totalCoverage")
	private Integer totalCoverage;

	@Field( "firstScraping")
	@NonNull
	private Boolean firstScraping;

	@Field( "scrapedAt")
	private Date scrapedAt;

	CoverageRegion(@NonNull RegionProp properties, Integer totalCountries, boolean firstScraping) {
		this.properties = properties;
		this.totalCountries = totalCountries;
		this.firstScraping = firstScraping;
		this.scrapedAt = new Date();
	}

	public static CoverageRegion createCoverage(@NonNull RegionProp properties, Integer countriesQuantity){
		return new CoverageRegion(properties, countriesQuantity, true);
	}

	public void setTotalCoverage(Integer countryCoveragedQuantity){
		this.totalCoverage = countryCoveragedQuantity * 100 / this.totalCoverage;
	}

}
