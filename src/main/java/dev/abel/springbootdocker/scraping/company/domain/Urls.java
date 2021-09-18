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

    public Urls(String profile, String financialSummary) {
        this.profile = profile;
        this.financialSummary = financialSummary;
    }


    public static String buildSummaryUrl(String codeCompany) {

        StringBuilder summaryUrl = new StringBuilder();

        summaryUrl.append(Url.share.replace("#", codeCompany));

        return summaryUrl.toString();
    }

    public static String buildProfileUrl(String url) {

        StringBuilder summaryUrl = new StringBuilder();
        summaryUrl.append(url);
        summaryUrl.append("-company-profile");

        return summaryUrl.toString();

    }
}
