package dev.abel.springbootdocker.scraping.country.domain;

import dev.abel.springbootdocker.utils.GenerateUUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class EncodedData {

    @Id
    private String id;

    @Field("encodedCountry")
    private EncodedCountry encodeCountry;

    @Field("encodedMarketIndex")
    private List<EncodedMarketIndex> encodedMarketIndex;

    public EncodedData(EncodedCountry encodeCountry, List<EncodedMarketIndex> encodedMarketIndex) {
        this.id = GenerateUUID.generateUniqueId();
        this.encodeCountry = encodeCountry;
        this.encodedMarketIndex = encodedMarketIndex;
    }

    public EncodedMarketIndex getAllSharesMarketIndex() throws Exception {

        EncodedMarketIndex encodedMarketindex = this.encodedMarketIndex.stream().filter(enc -> enc.getCode().equalsIgnoreCase("all")).findFirst().get();
        if(encodedMarketindex == null)
            throw new Exception(String.format("Country:  %s, not have all shares", encodeCountry.getTitle()));


        return encodedMarketindex;
    }

}
