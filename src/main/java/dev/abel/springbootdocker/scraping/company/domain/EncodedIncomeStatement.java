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
        StringBuilder stringBuffer = new StringBuilder();

        if (totalRevenue.isEmpty()) {
            stringBuffer.append("totalRevenue title is empty-");
        }
        if (grossProfit.isEmpty()) {
            stringBuffer.append("grossProfit title is empty-");
        }
        if (operatingIncome.isEmpty()) {
            stringBuffer.append("operatingIncome title is empty-");
        }
        if (netIncome.isEmpty()) {
            stringBuffer.append("netIncome title is empty-");
        }

        if (grossMargin.isEmpty()) {
            stringBuffer.append("grossMargin title is empty-");
        }
        if (OperatingMargin.isEmpty()) {
            stringBuffer.append("OperatingMargin title is empty-");
        }
        if (NetProfitMargin.isEmpty()) {
            stringBuffer.append("NetProfitMargin title is empty-");
        }
        if (ReturnOnInvestment.isEmpty()) {
            stringBuffer.append("ReturnOnInvestment title is empty-");
        }
        return stringBuffer.length() > 0 ? stringBuffer.toString() : "true";

    }
}