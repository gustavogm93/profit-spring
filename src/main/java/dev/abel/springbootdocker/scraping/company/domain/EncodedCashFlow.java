package dev.abel.springbootdocker.scraping.company.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;


public class EncodedCashFlow {

    @Id
    private String id;

    //CASH FLOW
    @Field("CashFromOperatingActivities")
    private String CashFromOperatingActivities;

    @Field("CashFromInvestingActivities")
    private String CashFromInvestingActivities;

    @Field("CashFromFinancingActivities")
    private String CashFromFinancingActivities;

    @Field("NetChangeInCash")
    private String NetChangeInCash;

    //side
    @Field("Cash_FlowByShare")
    private String Cash_FlowByShare;

    @Field("RevenueByShare")
    private String RevenueByShare;

    @Field("Operating_Cash_Flow")
    private String Operating_Cash_Flow;
}
