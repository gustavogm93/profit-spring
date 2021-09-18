package dev.abel.springbootdocker.scraping.company.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashMap;

@Data
public class EncodedBalanceSheet {

    @Id
    private String id;

    //BALANCE SHEET
    @Field("TotalAssets")
    private HashMap<String,String> totalAssets;

    @Field("TotalLiabilities")
    private HashMap<String,String> totalLiabilities;

    @Field("TotalEquity")
    private HashMap<String,String> totalEquity;

    //side
    @Field("QuickRatio")
    private String quickRatio;

    @Field("CurrentRatio")
    private String currentRatio;

    @Field("LTDebtToEquity")
    private String lTDebtToEquity;

    @Field("Total_Debt_to_Equity")
    private String total_Debt_to_Equity;

    public EncodedBalanceSheet(){}


    public void fullValues(HashMap<String, String> totalAssets, HashMap<String, String> totalLiabilities, HashMap<String, String> totalEquity) {
        this.totalAssets = totalAssets;
        this.totalLiabilities = totalLiabilities;
        this.totalEquity = totalEquity;
    }

    public void fullPercentajes(String quickRatio, String currentRatio, String lTDebtToEquity, String total_Debt_to_Equity) {
        this.quickRatio = quickRatio;
        this.currentRatio = currentRatio;
        this.lTDebtToEquity = lTDebtToEquity;
        this.total_Debt_to_Equity = total_Debt_to_Equity;
    }

    public String verifyValidEncoded() {

        if (totalAssets.isEmpty()) {
            return "totalAssets is empty";
        }
        if (totalLiabilities.isEmpty()) {
            return "totalLiabilities is empty";
        }
        if (totalEquity.isEmpty()) {
            return "totalEquity is empty";
        }
        if (quickRatio.isEmpty()) {
            return "quickRatio is empty";
        }
        if (currentRatio.isEmpty()) {
            return "currentRatio is empty";
        }
        if (lTDebtToEquity.isEmpty()) {
            return "LTDebtToEquity is empty";
        }
        if (total_Debt_to_Equity.isEmpty()) {
            return "total_Debt_to_Equity Cash Flow is empty";
        }


        return "true";
    }
}
