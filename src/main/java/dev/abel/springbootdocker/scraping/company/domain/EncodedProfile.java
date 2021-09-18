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



    public EncodedProfile(String industry, String sector, String employees, String equityType) {
        this.industry = industry;
        this.sector = sector;
        this.employees = employees;
        this.equityType = equityType;
    }


    public String verifyValidEncoded() {
        if (industry.isEmpty()) {
            return "industry title is empty";
        }

        if (sector.isEmpty()) {
            return "sector title is empty";
        }

        if (employees.isEmpty()) {
            return "employees title is empty";
        }

        if (equityType.isEmpty()) {
            return "equityType title is empty";
        }

        if (equityType.isEmpty()) {
            return "equityType title is empty";
        }

        return "true";
    }
}
