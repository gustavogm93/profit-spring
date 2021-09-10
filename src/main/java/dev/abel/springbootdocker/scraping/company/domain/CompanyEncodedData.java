package dev.abel.springbootdocker.scraping.company.domain;

import dev.abel.springbootdocker.collections.country.CountryProp;
import dev.abel.springbootdocker.collections.region.RegionProp;
import dev.abel.springbootdocker.enums.utils.Url;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "CompanyScrapedData")
@Data
public class CompanyEncodedData {

    @Id
    private String id;

    @Field("location")
    private @NonNull Location location;

    @Field("title")
    private @NonNull String title;

    @Field("code")
    private @NonNull String code;

    @Field("currency")
    private @NonNull String currency;

    @Field("encodedProfile")
    private EncodedProfile encodedProfile;

    @Field("encodedIncomeStatement")
    private EncodedIncomeStatement encodedIncomeStatement;

    @Field("encodedCashFlow")
    private EncodedCashFlow encodedCashFlow;

    @Field("urls")
    private Urls urls;

    @Field("lastUpdateAt")
    private Date lastUpdateAt;

    @Field("errors")
    private String errors;

    public CompanyEncodedData(@NonNull String title, @NonNull String companyCode, @NonNull Location location) {
        this.title = title;
        this.code = companyCode;
        this.location = location;
        this.lastUpdateAt = new Date();
        this.urls = new Urls(companyCode);
    }



}
