package dev.abel.springbootdocker.scraping.company.domain;

import dev.abel.springbootdocker.enums.utils.Url;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Urls {

    @Field("profile")
    private String profile;

    @Field("financialSummary")
    private String financialSummary;


    public Urls(String companyCode) {
        this.profile = buildSummaryUrl(companyCode);
        this.financialSummary = buildProfileUrl(companyCode);
    }

    public String buildSummaryUrl(String codeCompany) {

        StringBuilder summaryUrl = new StringBuilder();

        summaryUrl.append(Url.share.replace("#", codeCompany));

        return summaryUrl.toString();
    }

    public String buildProfileUrl(String codeCompany) {

        StringBuilder summaryUrl = new StringBuilder();

        summaryUrl.append(Url.profile.replace("#", codeCompany));

        return summaryUrl.toString();

    }
}
