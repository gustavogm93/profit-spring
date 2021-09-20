package dev.abel.springbootdocker.scraping.company.domain;

import dev.abel.springbootdocker.collections.country.CountryProp;
import dev.abel.springbootdocker.collections.region.RegionProp;
import dev.abel.springbootdocker.enums.utils.Url;
import dev.abel.springbootdocker.scraping.company.application.ScrapingCompanyStrategy;
import dev.abel.springbootdocker.scraping.country.domain.EncodedCountry;
import dev.abel.springbootdocker.scraping.country.domain.EncodedData;
import dev.abel.springbootdocker.scraping.country.domain.EncodedMarketIndex;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "companyScrapedData")
@Data
public class CompanyScrapedData {

    @Id
    private String id;

    @Field("location")
    private Location location;

    @Field("title")
    private String title;

    @Field("code")
    private String code;

    @Field("encodedCompanyData")
    private EncodedCompanyData encodeData;

    @Field("previousEncodedCompanyData")
    private EncodedCompanyData previousEncodeData;

    @Field("urls")
    private Urls urls;

    @Field("errors")
    private Map<String, String> errors;

    @Field("lastUpdateAt")
    private Date lastUpdateAt;

    private static final Logger logger = LoggerFactory.getLogger(ScrapingCompanyStrategy.class);

    public CompanyScrapedData(String title, String code, Location location) {
        this.id = code;
        this.title = title;
        this.code = code;
        this.location = location;
        this.lastUpdateAt = new Date();
        this.errors = new HashMap<>();
    }

    public void generateUrls(String url)
    {
        String summaryUrl = Urls.buildSummaryUrl(this.code);
        String profileUrl = Urls.buildProfileUrl(url);

        if(!summaryUrl.isEmpty() && !profileUrl.isEmpty()) {
            this.urls = new Urls(profileUrl, summaryUrl);
        }
        this.lastUpdateAt = new Date();
    }

    public void fillCompanyEncodedData(EncodedCompanyData encodeData){
        //Previuous encode data for have a backup
        //PASAR A ADELANTE Y VERIFICAR PARA REMPLAZ
        if(this.encodeData != null) {
            this.previousEncodeData = this.encodeData;
            this.encodeData = null;
        }

        this.errors = new HashMap<String,String>();

        EncodedProfile encodedProfileData = encodeData.getEncodedProfile();
        verifyProfile(encodedProfileData);

        EncodedBalanceSheet encodedBalanceSheetData = encodeData.getEncodedBalanceSheet();
        EncodedCashFlow encodedCashFlowData = encodeData.getEncodedCashFlow();
        EncodedIncomeStatement encodeDataEncodedIncomeStatement = encodeData.getEncodedIncomeStatement();

        if(encodedBalanceSheetData == null || encodedCashFlowData == null || encodeDataEncodedIncomeStatement == null){
            this.errors.put("general","Document error");
            return;
        }

        String validationEncodedProfileData = encodedProfileData.verifyValidEncoded();
        String validationEncodedBalanceSheetData = encodedBalanceSheetData.verifyValidEncoded();
        String validationEncodedCashFlowData = encodedCashFlowData.verifyValidEncoded();
        String validationEncodedIncomeStatement = encodeDataEncodedIncomeStatement.verifyValidEncoded();

        if(!validationEncodedProfileData.equalsIgnoreCase("true")){
            errors.put("encodedProfileData",validationEncodedProfileData);
        }
        if(!validationEncodedBalanceSheetData.equalsIgnoreCase("true")){
            errors.put("encodedBalanceSheetData",validationEncodedBalanceSheetData);
        }
        if(!validationEncodedCashFlowData.equalsIgnoreCase("true")){
            errors.put("encodedCashFlowData",validationEncodedCashFlowData);
        }
        if(!validationEncodedIncomeStatement.equalsIgnoreCase("true")){
            errors.put("encodedIncomeStatementData",validationEncodedIncomeStatement);
        }

        if(errors.size() > 0)
        logger.warn("Errors was encountered in {}, : {} ", this.title,errors.toString());

        this.encodeData = encodeData;
    }

    public void verifyProfile(EncodedProfile encodedProfileData){
        if(encodedProfileData == null){this.errors.put("encodedProfileData","Document error");
            return;
        }
        String validationEncodedProfileData = encodedProfileData.verifyValidEncoded();

        this.errors.put("encodedProfileData",validationEncodedProfileData);
    }


    public void fullIfEmptyHtml(){
        this.errors.put("encodedBalanceSheetData", "empty");
        this.errors.put("encodedCashFlowData", "empty");
        this.errors.put("encodedIncomeStatementData", "empty");
    }

}
