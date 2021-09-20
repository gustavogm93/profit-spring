package dev.abel.springbootdocker.scraping.company.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
@Data
public class EncodedProfile {

    @Id
    private String id;
    //PROFILE ::::::::::::
    @Field("industry")
    private String industry;

    @Field("sector")
    private String sector;

    @Field("employees")
    private String employees;

    @Field("equityType")
    private String equityType;

    @Field("currency")
    private String currency;

    public EncodedProfile(String industry, String sector, String employees, String equityType, String currency) {
        this.industry = industry;
        this.sector = sector;
        this.employees = employees;
        this.equityType = equityType;
        this.currency = currency;
    }

    public String verifyValidEncoded() {
        StringBuffer stringBuffer = new StringBuffer();

        if (industry.isEmpty()) {
            stringBuffer.append("industry title is empty");
        }

        if (sector.isEmpty()) {
            stringBuffer.append("sector title is empty");
        }

        if (employees.isEmpty()) {
            stringBuffer.append("employees title is empty");
        }

        if (equityType.isEmpty()) {
            stringBuffer.append("equityType title is empty");
        }

        if (equityType.isEmpty()) {
            stringBuffer.append("equityType title is empty");
        }
        return stringBuffer.length() > 0 ? stringBuffer.toString() : "true";
    }
}
