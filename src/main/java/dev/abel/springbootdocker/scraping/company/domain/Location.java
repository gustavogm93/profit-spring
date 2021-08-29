package dev.abel.springbootdocker.scraping.company.domain;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Location {

    @Id
    private String id;

    @Field("countryTitle")
    private @NonNull String countryTitle;

    @Field("countryCode")
    private @NonNull String countryCode;

    @Field("regionCode")
    private @NonNull String regionCode;

    @Field("regionTitle")
    private @NonNull String regionTitle;

    public Location(@NonNull String countryTitle, @NonNull String countryCode, @NonNull String regionTitle, @NonNull String regionCode) {
        this.countryTitle = countryTitle;
        this.countryCode = countryCode;
        this.regionCode = regionCode;
        this.regionTitle = regionTitle;
    }
}
