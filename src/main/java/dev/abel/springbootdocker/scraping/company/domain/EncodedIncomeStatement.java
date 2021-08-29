package dev.abel.springbootdocker.scraping.company.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class EncodedIncomeStatement {

    @Id
    private String id;

    //COMMERCE VALUES::::::::::::
    //INCOME STATEMENT
    @Field("totalRevenue")
    private String totalRevenue;

    @Field("grossProfit")
    private String grossProfit;

    @Field("operatingIncome")
    private String operatingIncome;

    @Field("netIncome")
    private String netIncome;

    @Field("grossMargin")
    private String grossMargin;

    @Field("Operatingmargin")
    private String OperatingMargin;

    @Field("NetProfitMargin")
    private String NetProfitMargin;

    @Field("ReturnOnInvestment")
    private String ReturnOnInvestment;
}
