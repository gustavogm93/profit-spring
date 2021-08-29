package dev.abel.springbootdocker.scraping.company.domain;

import dev.abel.springbootdocker.collections.country.CountryProp;
import dev.abel.springbootdocker.collections.region.RegionProp;
import dev.abel.springbootdocker.enums.utils.Url;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "CompanyEncodedData")
@Data
public class CompanyEncodedData {

    @Id
    private String id;

    @Field("country")
    private CountryProp country;

    @Field("region")
    private RegionProp region;

    @Field("title")
    private String title;

    @Field("code")
    private String code;

    @Field("currency")
    private String currency;

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

    public CompanyEncodedData(String title, String companyCode, CountryProp country, RegionProp region, Date lastUpdateAt) {
        this.title = title;
        this.code = companyCode;
        this.country = country;
        this.region = region;
        this.lastUpdateAt = lastUpdateAt;
        this.urls = new Urls(companyCode);
    }



}
