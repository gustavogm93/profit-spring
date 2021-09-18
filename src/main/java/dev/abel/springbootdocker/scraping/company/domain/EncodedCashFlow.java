package dev.abel.springbootdocker.scraping.company.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashMap;

@Data
public class EncodedCashFlow {

    @Id
    private String id;

    //CASH FLOW
    @Field("CashFromOperatingActivities")
    private HashMap<String,String> CashFromOperatingActivities;

    @Field("CashFromInvestingActivities")
    private HashMap<String,String> CashFromInvestingActivities;

    @Field("CashFromFinancingActivities")
    private HashMap<String,String> CashFromFinancingActivities;

    @Field("NetChangeInCash")
    private HashMap<String,String> NetChangeInCash;

    //side
    @Field("Cash_FlowByShare")
    private String Cash_FlowByShare;

    @Field("RevenueByShare")
    private String RevenueByShare;

    @Field("Operating_Cash_Flow")
    private String Operating_Cash_Flow;

    public EncodedCashFlow() {}

    public void fullPercentaje(String cash_FlowByShare, String revenueByShare, String operating_Cash_Flow) {
        Cash_FlowByShare = cash_FlowByShare;
        RevenueByShare = revenueByShare;
        Operating_Cash_Flow = operating_Cash_Flow;
    }

    public void fullValues(HashMap<String, String> cashFromOperatingActivities, HashMap<String, String> cashFromInvestingActivities,
                           HashMap<String, String> cashFromFinancingActivities, HashMap<String, String> netChangeInCash) {
        CashFromOperatingActivities = cashFromOperatingActivities;
        CashFromInvestingActivities = cashFromInvestingActivities;
        CashFromFinancingActivities = cashFromFinancingActivities;
        NetChangeInCash = netChangeInCash;
    }

    public String verifyValidEncoded() {
        if (CashFromOperatingActivities.isEmpty()) {
            return "CashFromOperatingActivities is empty";
        }
        if (CashFromInvestingActivities.isEmpty()) {
            return "CashFromInvestingActivities is empty";
        }

        if (CashFromFinancingActivities.isEmpty()) {
            return "CashFromFinancingActivities is empty";
        }

        if (NetChangeInCash.isEmpty()) {
            return "netChangeInCash is empty";
        }

        if (Cash_FlowByShare.isEmpty()) {
            return "cash_FlowByShare is empty";
        }

        if (RevenueByShare.isEmpty()) {
            return "revenueByShare is empty";
        }
        if (Operating_Cash_Flow.isEmpty()) {
            return "operating Cash Flow is empty";
        }


        return "true";
    }
}
