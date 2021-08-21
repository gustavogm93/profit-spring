package dev.abel.springbootdocker.collections.summary;

import dev.abel.springbootdocker.collections.marketIndex.MarketIndexProp;
import lombok.Data;

import java.util.Comparator;

@Data
public class SummaryMarketIndexData {

	MarketIndexProp marketIndex;
	Integer sharesQuantity;
	
	public SummaryMarketIndexData(MarketIndexProp marketIndex, Integer sharesQuantity) {
		this.marketIndex = marketIndex;
		this.sharesQuantity = sharesQuantity;
	}
	
	public static Comparator<SummaryMarketIndexData> byTitle = Comparator.comparing(SummaryMarketIndexData::getTitle);
	
	public String getTitle() {
		return this.marketIndex.getTitle();
	}
}	
