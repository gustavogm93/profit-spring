package dev.abel.springbootdocker.collections.marketIndex;

import dev.abel.springbootdocker.collections.marketIndex.MarketIndexProp;
import lombok.AllArgsConstructor;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@Data
public class CoverageMarketIndex {
	
	@Id
	private final String id;
	
	@Field( "country")
	private final MarketIndexProp marketIndexProp;
	
	private final Integer totalShares; 
	
}
