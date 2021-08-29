package dev.abel.springbootdocker.scraping.company.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

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

    @Field("currencyTransform")
    private String currencyTransform;
}
