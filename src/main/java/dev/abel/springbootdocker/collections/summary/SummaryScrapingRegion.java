package dev.abel.springbootdocker.collections.summary;

import dev.abel.springbootdocker.collections.region.RegionProp;
import lombok.AllArgsConstructor;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Document(collection = "summary-scraping-data-region")
@AllArgsConstructor
public class SummaryScrapingRegion {
	
	@Id
	String _id;
	
	@Field( "Region")
	@NotNull RegionProp region;
	
	@Field( "Countries")
	@NotNull Set<SummaryCountry> countries;

}
