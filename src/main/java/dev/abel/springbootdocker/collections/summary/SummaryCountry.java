package dev.abel.springbootdocker.collections.summary;

import dev.abel.springbootdocker.collections.country.CountryProp;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
public class SummaryCountry {

	@NotNull private CountryProp Country;
	@NotNull private Set<SummaryMarketIndexData> summaryMarketIndex;

}
