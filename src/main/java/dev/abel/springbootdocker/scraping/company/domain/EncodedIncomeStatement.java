package dev.abel.springbootdocker.scraping.company.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashMap;

@Data
public class EncodedIncomeStatement {

    @Id
    private String id;

    //COMMERCE VALUES::::::::::::
    //INCOME STATEMENT
    @Field("totalRevenue")
    private HashMap<String,String> totalRevenue;

    @Field("grossProfit")
    private HashMap<String,String> grossProfit;

    @Field("operatingIncome")
    private HashMap<String,String> operatingIncome;

    @Field("netIncome")
    private HashMap<String,String> netIncome;

    @Field("grossMargin")
    private String grossMargin;

    @Field("Operatingmargin")
    private String OperatingMargin;

    @Field("NetProfitMargin")
    private String NetProfitMargin;

    @Field("ReturnOnInvestment")
    private String ReturnOnInvestment;

    public EncodedIncomeStatement() {}

    public void fullPercentage(String Grossmargin,String Operatingmargin,String NetProfitMargin, String ReturnOnInvestment){
        this.grossMargin = Grossmargin;
        this.OperatingMargin = Operatingmargin;
        this.NetProfitMargin = NetProfitMargin;
        this.ReturnOnInvestment = ReturnOnInvestment;
    }

    public void fullValues(HashMap<String,String> TotalRevenue,HashMap<String,String> GrossProfit,
                           HashMap<String,String> OperatingIncome, HashMap<String,String> NetIncome){
        this.totalRevenue = TotalRevenue;
        this.grossProfit = GrossProfit;
        this.operatingIncome = OperatingIncome;
        this.netIncome = NetIncome;
    }

    public String verifyValidEncoded() {

        if (totalRevenue.isEmpty()) {
            return "totalRevenue title is empty";
        }
        if (grossProfit.isEmpty()) {
            return "grossProfit title is empty";
        }
        if (operatingIncome.isEmpty()) {
            return "operatingIncome title is empty";
        }
        if (netIncome.isEmpty()) {
            return "netIncome title is empty";
        }

        if (grossMargin.isEmpty()) {
            return "grossMargin title is empty";
        }
        if (OperatingMargin.isEmpty()) {
            return "OperatingMargin title is empty";
        }
        if (NetProfitMargin.isEmpty()) {
            return "NetProfitMargin title is empty";
        }
        if (ReturnOnInvestment.isEmpty()) {
            return "ReturnOnInvestment title is empty";
        }

        return "true";

    }
}