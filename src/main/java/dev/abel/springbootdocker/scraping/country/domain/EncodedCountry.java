package dev.abel.springbootdocker.scraping.country.domain;

import dev.abel.springbootdocker.utils.GenerateUUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class EncodedCountry {

    @Id
    private String id;

    @Field("code")
    private String code;

    @Field("title")
    private String title;

    @Field("url")
    private String url;

    public EncodedCountry(String code, String title, String url) {
        this.id = GenerateUUID.generateUniqueId();
        this.code = code;
        this.title = title;
        this.url = url;
    }

    public String verifyValidEncoded() {

        try {
            Integer.parseInt(this.code);
        } catch (Exception e) {
            return "country invalid code";
        }

        if (title.isEmpty()) {
            return "country title is empty";
        }

        if (url.isEmpty()) {
            return "url is empty";
        }

        return "true";
    }

}


