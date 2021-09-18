package dev.abel.springbootdocker.scraping.company.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
public class EncodedCompanyData {

    @Field("encodedProfile")
    private EncodedProfile encodedProfile;

    @Field("encodedIncomeStatement")
    private EncodedIncomeStatement encodedIncomeStatement;

    @Field("encodedCashFlow")
    private EncodedCashFlow encodedCashFlow;

    @Field("encodedBalanceSheet")
    private EncodedBalanceSheet encodedBalanceSheet;



    public EncodedCompanyData(EncodedProfile encodedProfile, EncodedIncomeStatement encodedIncomeStatement, EncodedCashFlow encodedCashFlow, EncodedBalanceSheet encodedBalanceSheet) {
        this.encodedProfile = encodedProfile;
        this.encodedIncomeStatement = encodedIncomeStatement;
        this.encodedCashFlow = encodedCashFlow;
        this.encodedBalanceSheet = encodedBalanceSheet;
    }

}
