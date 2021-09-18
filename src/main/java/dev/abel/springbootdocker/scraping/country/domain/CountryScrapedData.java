package dev.abel.springbootdocker.scraping.country.domain;

import dev.abel.springbootdocker.collections.country.CountryProp;
import dev.abel.springbootdocker.collections.region.RegionProp;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document(collection = "countryScrapedData")
@Data
public class CountryScrapedData {

    @Id
    private String id;

    @Field("country")
    private CountryProp country;

    @Field("region")
    private RegionProp region;

    @Field("encodeData")
    private EncodedData encodeData;

    @Field("previousEncodeData")
    private EncodedData previousEncodedData;

    @Field("error")
    private String error;

    @Field("lastUpdate")
    private Date lastUpdate;

    public CountryScrapedData(String id, CountryProp country, RegionProp region) {
        this.id = id;
        this.country = country;
        this.region = region;
    }

    public boolean fillCountryScrapedData(EncodedData encodeData){
        //Previuous encode data for have a backup
        if(this.encodeData != null) {
            this.previousEncodedData = this.encodeData;
            this.encodeData = null;
        }

        EncodedCountry encodedCountry = encodeData.getEncodeCountry();
        StringBuffer errors = new StringBuffer();
        String validationCountry = encodedCountry.verifyValidEncoded();
            if(!validationCountry.equalsIgnoreCase("true")){
                errors.append(validationCountry);
            }

        List<EncodedMarketIndex> marketIndexes = encodeData.getEncodedMarketIndex();

        for(EncodedMarketIndex marketIndex: marketIndexes){
            String validationMarketIndex = marketIndex.verifyValidEncoded();
            if(!validationMarketIndex.equalsIgnoreCase("true")){
                errors.append(" ".concat(validationMarketIndex));
            }
        }

        if(errors.length() > 0 ){
            this.error = errors.toString();
        }

        this.encodeData = encodeData;
        this.lastUpdate = new Date();
        return errors.length() == 0;
    }





}
