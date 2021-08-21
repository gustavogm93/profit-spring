package dev.abel.springbootdocker.collections.marketIndex;

import dev.abel.springbootdocker.collections.share.ShareProp;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Comparator;
import java.util.Set;

@AllArgsConstructor
@Document(collection = "market-Index-constant")
@Data
public class MarketIndexDTO {

	@Id
	private final String id;

	@Field( "CountryId")
	private final String countryId;

	@Field( "propierties")
	private final MarketIndexProp propierties;

	@Field( "Shares")
	private final Set<ShareProp> shares;

	public String getTitle() {
		return this.propierties.getTitle();
	}

	public String getCode() {
		return this.propierties.getCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		MarketIndexDTO other = (MarketIndexDTO) obj;

		if (!other.id.equals(this.id))
			return false;

		if (!other.propierties.getCode().equals(this.propierties.getCode()))
			return false;
		
		return true;
	}
	
	public static Comparator<MarketIndexDTO> byTitle = Comparator.comparing(MarketIndexDTO::getTitle);
	
	public static Comparator<MarketIndexDTO> byCode = Comparator.comparing(MarketIndexDTO::getTitle);
	
	
}
