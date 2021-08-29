package dev.abel.springbootdocker.scraping.country.domain;

import dev.abel.springbootdocker.collections.country.CountryProp;
import dev.abel.springbootdocker.collections.region.RegionProp;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "dataSource")
@Data
public class DataSource {

    @Id
    private String id;

    @Field("country")
    private CountryProp country;

    @Field("region")
    private RegionProp region;

    @Field("marketIndexes")
    private List<String> marketIndexes;

    @Field("shares")
    private List<String> shares;

    public DataSource(CountryProp country, RegionProp region) {
        this.country = country;
        this.region = region;
    }
}
