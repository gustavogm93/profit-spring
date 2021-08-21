package dev.abel.springbootdocker.generics;


import dev.abel.springbootdocker.collections.marketIndex.MarketIndexDTO;
import dev.abel.springbootdocker.collections.marketIndex.MarketIndexProp;
import dev.abel.springbootdocker.collections.share.ShareDTO;
import dev.abel.springbootdocker.collections.share.ShareProp;
import com.google.common.base.Function;

public class Mapper {

	/*
	public static Function<FailedRegionDTO, RegionDTO> failedToRegion = new Function<FailedRegionDTO, RegionDTO>() {

		@Override
		public RegionDTO apply(FailedRegionDTO failedRegion) {
			return new RegionDTO(failedRegion.getId(), failedRegion.getRegion(), failedRegion.getCountries());

		}

	};
	*/
	public static Function<ShareProp, ShareDTO> sharePropToShareDTO = new Function<ShareProp, ShareDTO>() {

		@Override
		public ShareDTO apply(ShareProp share) {
			return new ShareDTO(share.getCode(), share);
		}

	};
	
	public static Function<MarketIndexDTO, MarketIndexProp> MarketIndexDtoToMarketIndexProp = new Function<MarketIndexDTO, MarketIndexProp>() {

		@Override
		public MarketIndexProp apply(MarketIndexDTO marketIndexDTO) {
			return marketIndexDTO.getPropierties();
		}

	};

}
