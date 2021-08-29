package dev.abel.springbootdocker.scraping.company.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class EncodedBalanceSheet {

    @Id
    private String id;

    //BALANCE SHEET
    @Field("TotalAssets")
    private String TotalAssets;

    @Field("TotalLiabilities")
    private String TotalLiabilities;

    @Field("TotalEquity")
    private String TotalEquity;

    //side
    @Field("QuickRatio")
    private String QuickRatio;

    @Field("CurrentRatio")
    private String CurrentRatio;

    @Field("LTDebtToEquity")
    private String LTDebtToEquity;

    @Field("Total_Debt_to_Equity")
    private String Total_Debt_to_Equity;
}
