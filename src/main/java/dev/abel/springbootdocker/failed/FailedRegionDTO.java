package dev.abel.springbootdocker.failed;

import dev.abel.springbootdocker.collections.country.CountryProp;
import dev.abel.springbootdocker.collections.region.RegionProp;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Document(collection = "Failed")
@Data
public class FailedRegionDTO {
	
	@Id
	private String id;
	
	@Field( "region")
	private final RegionProp region;
	
	@Field( "countries")
	private final Set<CountryProp> countries;

	public FailedRegionDTO(String id, RegionProp region, Set<CountryProp> countries) {
		this.id = id;
		this.region = region;
		this.countries = countries;
	}

}
